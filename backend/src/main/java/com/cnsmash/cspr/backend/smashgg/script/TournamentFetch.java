package com.cnsmash.cspr.backend.smashgg.script;

import com.alibaba.fastjson.JSONArray;
import com.cnsmash.cspr.api.dto.StandingDto;
import com.cnsmash.cspr.api.entity.Participate;
import com.cnsmash.cspr.api.entity.Player;
import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.cnsmash.cspr.api.service.IPlayerService;
import com.cnsmash.cspr.api.service.IStatsService;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.backend.smashgg.graphql.GraphQLScript;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class TournamentFetch {

    @Autowired
    static GraphQLScript graphQLScript;

    @Autowired
    static ITournamentService iTournamentService = (ITournamentService) ApiSpringUtil.getBean(ITournamentService.class);

    @Autowired
    static IPlayerService iPlayerService = (IPlayerService) ApiSpringUtil.getBean(IPlayerService.class);

    @Autowired
    static IParticipateService iParticipateService = (IParticipateService) ApiSpringUtil.getBean(IParticipateService.class);

    @Autowired
    static IStatsService iStatsService = (IStatsService) ApiSpringUtil.getBean(IStatsService.class);

    public static void getTournamentByLeague(long leagueId) {
        List<Long> tournamentList = graphQLScript.getTournamentListByLeague(leagueId);
        for (long tournamentId: tournamentList) {
            getTournamentDetail(tournamentId);
            /*
            if (iTournamentService.getById(tournamentId) != null) {
                updateTournament(tournamentId);
            } else {
                getTournamentDetail(tournamentId);
            }
            */
        }
    }

    public static void getTournamentDetail(long tournamentId) {
        // 获取之前已经存好的比赛信息
        Tournament savedTournament = iTournamentService.getById(tournamentId);
        int oldStatus = 0;
        if (savedTournament != null) {
            oldStatus = savedTournament.getStatus();
        }
        Tournament tournament = graphQLScript.getTournamentDetail(tournamentId);
        iTournamentService.saveOrUpdate(tournament);
        if (tournament.getStatus() == 2) {
            // 已经存好的比赛状态不是未完成，才去抓取比赛排名
            if (oldStatus != 2) {
                getTournamentStanding(tournamentId);
            }
            SetFetch.getSetByTournament(tournamentId);
            updateTournamentCharacter(tournamentId);
        }
    }

    public static void getTournamentStanding(long tournamentId) {
        int page = 0;
        boolean stopFlag = false;
        List<StandingDto> standingList = new LinkedList<>();
        while (stopFlag == false) {
            page += 1;
            List<StandingDto> tmpList = GraphQLScript.getTournamentStanding(tournamentId, page);
            standingList.addAll(tmpList);
            if (tmpList.size() <= 10) {
                stopFlag = true;
            }
        }
        // display result
        List<Map<String, String>> displayResult = new LinkedList<>();
        for (int i=1; i<=3; i++) {
            displayResult.add(new HashMap<>());
        }
        for (StandingDto standingDto: standingList) {
            Long playerId = standingDto.getPlayerId();
            // 用户信息不存在
            if (iPlayerService.getById(playerId) == null) {
                Player player = GraphQLScript.getPlayerDetail(playerId);
                iPlayerService.newPlayer(player);
            }
            Boolean isDisqualified = standingDto.getIsDisqualified();
            Participate participate = new Participate();
            participate.setParticipateId(playerId + "-" + tournamentId);
            participate.setPlayerId(playerId);
            participate.setTournamentId(tournamentId);
            participate.setStanding(standingDto.getStanding());
            participate.setIsDisqualified(isDisqualified ? 1 : 0);
            iParticipateService.saveOrUpdate(participate);

            iStatsService.updateTournamentCount(playerId, 1);

            if (!isDisqualified) {
                iStatsService.updateLastTournament(playerId);
            }

            int standing = standingDto.getStanding();
            if (standing <= 3) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("name", standingDto.getPlayerTag());
                displayResult.set(standing - 1, resultMap);
            }
        }

        Gson gson = new Gson();
        String displayResultStr = String.valueOf(gson.toJson(displayResult));
        iTournamentService.setDisplayResult(tournamentId, displayResultStr);

    }

    public static void updateTournamentCharacter(long tournamentId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("tournament_id", tournamentId);
        List<Participate> participateList = iParticipateService.listByMap(queryMap);

        // 获取display result并更新角色数据
        Tournament tournament = iTournamentService.getById(tournamentId);
        String displayResult = tournament.getDisplayResult();
        List<Object> resultList = JSONArray.parseArray(displayResult);

        for (Participate participate: participateList) {
            String character = iParticipateService.countTournamentCharacter(tournamentId, participate.getPlayerId());
            if ("".equals(character)) character = null;
            participate.setCharacter(character);
            iParticipateService.updateById(participate);
            if (character == null) return;
            int standing = participate.getStanding();
            if (standing <= 3) {
                ((Map)resultList.get(standing - 1)).put("icon", character.split(",")[0]);
            }

        }

        Gson gson = new Gson();
        String displayResultStr = String.valueOf(gson.toJson(resultList));
        iTournamentService.setDisplayResult(tournamentId, displayResultStr);
    }

}
