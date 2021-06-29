package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.dto.PlayerTournamentFilterDto;
import com.cnsmash.cspr.api.dto.TournamentFilterDto;
import com.cnsmash.cspr.api.dto.UpdateTournamentDto;
import com.cnsmash.cspr.api.entity.Participate;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.mapper.TournamentMapper;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.cnsmash.cspr.api.service.IPlayerService;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.vo.PlayerLite;
import com.cnsmash.cspr.api.vo.PlayerTournament;
import com.cnsmash.cspr.api.vo.TournamentDetail;
import com.cnsmash.cspr.api.vo.TournamentStanding;
import com.cnsmash.cspr.framework.dto.PaginationDto;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Slf4j
@Service
public class TournamentServiceImpl extends ServiceImpl<TournamentMapper, Tournament> implements ITournamentService {

    @Autowired
    TournamentMapper tournamentMapper;

    @Autowired
    ISetService iSetService;

    @Autowired
    IParticipateService iParticipateService;

    @Autowired
    IPlayerService iPlayerService;

    @Override
    public List<TournamentDetail> getRecentTournament() {
        return tournamentMapper.getRecentTournament(LocalDateTime.now().minusDays(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:ss:mm")));
    }

    @Override
    public List<PlayerTournament> getPlayerTournamentList(PlayerTournamentFilterDto playerTournamentFilterDto) {
        PaginationDto pagination = playerTournamentFilterDto.getPagination();
        PageHelper.startPage(pagination.getPageIndex(), pagination.getPageSize());
        List<PlayerTournament> tournamentList = tournamentMapper.getPlayerTournamentList(playerTournamentFilterDto);
        for (PlayerTournament tournament: tournamentList) {
            tournament.setSets(iSetService.getPlayerSetDetail(tournament.getPlayerId(), tournament.getTournamentId()));
        }
        return tournamentList;
    }

    @Override
    public void setDisplayResult(Long tournamentId, String displayResultStr) {
        tournamentMapper.setDisplayResult(tournamentId, displayResultStr);
    }

    @Override
    public List<TournamentDetail> getTournamentList(TournamentFilterDto tournamentFilterDto) {
        PaginationDto pagination = tournamentFilterDto.getPagination();
        log.info(tournamentFilterDto.toString());
        PageHelper.startPage(pagination.getPageIndex(), pagination.getPageSize());
        return tournamentMapper.getTournamentList(tournamentFilterDto);
    }

    @Override
    public TournamentDetail getTournamentDetail(long tournamentId) {
        return tournamentMapper.getTournamentDetail(tournamentId);
    }

    @Override
    public List<TournamentStanding> getTournamentStanding(long tournamentId) {
        // 把比赛排名列表查出来
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("tournament_id", tournamentId);
        List<Participate> participateList = iParticipateService.listByMap(queryMap);
        Map<Long, TournamentStanding> standingMap = new HashMap();
        // 获取所有参赛者简单数据
        for (Participate participate: participateList) {
            Long playerId = participate.getPlayerId();
            PlayerLite playerLite = iPlayerService.getPlayerLite(playerId);
            TournamentStanding tournamentStanding = new TournamentStanding(participate, playerLite);
            standingMap.put(playerId, tournamentStanding);
        }
        // 查出比赛的所有set
        List<Set> setList = iSetService.listByMap(queryMap);
        for (Set set: setList) {
            Long winnerId = set.getWinnerId();
            PlayerLite winnerLite = standingMap.get(winnerId).playerLite();
            Long loserId = set.getPlayer1Id().longValue() == winnerId ? set.getPlayer2Id() : set.getPlayer1Id();
            standingMap.get(loserId).addLosingPlayer(winnerLite);
            standingMap.get(winnerId).addWinSet();
        }
        // 分页与组装数据
        List<TournamentStanding> standingList = new LinkedList<>();
        for (TournamentStanding tournamentStanding: standingMap.values()) {
            standingList.add(tournamentStanding);
        }
        Collections.sort(standingList, new Comparator<TournamentStanding>() {
            @Override
            public int compare(TournamentStanding standing1, TournamentStanding standing2) {
                if (standing1.getStanding() > standing2.getStanding()) return 1;
                else if (standing1.getStanding() == standing2.getStanding()) return 0;
                return -1;
            }
        });
        return standingList;
    }

    public List<Tournament> getTournamentBySeason(int season) {
        return tournamentMapper.getTournamentBySeason(season);
    }

    public void updateTournamentLite(UpdateTournamentDto updateTournamentDto) {
        Tournament tournament = tournamentMapper.selectById(updateTournamentDto.getTournamentId());
        tournament.setCsprRate(updateTournamentDto.getCsprRate());
        tournament.setOnline(updateTournamentDto.getOnline());
        if (updateTournamentDto.getVod() != null) {
            tournament.setVod(updateTournamentDto.getVod());
        }
        tournamentMapper.updateById(tournament);
    }
}
