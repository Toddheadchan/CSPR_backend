package com.cnsmash.cspr.backend.script.cspr;

import com.cnsmash.cspr.api.entity.Log;
import com.cnsmash.cspr.api.entity.Participate;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.service.ILogService;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.backend.algorithm.glicko2.Rating;
import com.cnsmash.cspr.backend.algorithm.glicko2.RatingCalculator;
import com.cnsmash.cspr.backend.algorithm.glicko2.RatingPeriodResults;

import javax.annotation.Resource;
import java.util.*;

public class CsprManager {

    @Resource
    ISetService iSetService = (ISetService) ApiSpringUtil.getBean(ISetService.class);

    @Resource
    ITournamentService iTournamentService = (ITournamentService) ApiSpringUtil.getBean(ITournamentService.class);

    @Resource
    IParticipateService iParticipateService = (IParticipateService) ApiSpringUtil.getBean(IParticipateService.class);

    @Resource
    ILogService iLogService = (ILogService) ApiSpringUtil.getBean(ILogService.class);

    private Map<Long, Rating> playerMap;

    private RatingCalculator ratingSystem;

    private RatingPeriodResults results;

    private List<Log> logList;

    // 新赛季成绩重置
    private void newSeasonReset(int season) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("season", season - 1);
        List<Log> logList = iLogService.listByMap(queryMap);
        for (Log log: logList) {
            Rating player = new Rating(String.valueOf(log.getPlayerId()), this.ratingSystem);
            player.setRating(0.25 * (log.getNewCspr() - 1500) + 1500);
            this.playerMap.put(log.getPlayerId(), player);
        }
    }

    private Rating getPlayer(long playerId) {
        if (this.playerMap.keySet().contains(playerId) == false) {
            Rating player = new Rating(String.valueOf(playerId), this.ratingSystem);
            player.setRating(1500);
            playerMap.put(playerId, player);
        }
        return playerMap.get(playerId);
    }

    public void calculate(int season) {

        this.playerMap = new HashMap<>();
        this.ratingSystem = new RatingCalculator(0.06, 0.5);
        this.results = new RatingPeriodResults();
        this.logList = new LinkedList<>();

        // 若不是计算第0赛季，则获取选手上一赛季的成绩作新赛季成绩预设
        if (season != 0) {
            newSeasonReset(season);
        }

        // 获取赛季对应比赛列表
        List<Tournament> tournamentList = iTournamentService.getTournamentBySeason(season);

        for (Tournament tournament: tournamentList) {
            long tournamentId = tournament.getTournamentId();
            Map<Long, Double> oldCsprMap = new HashMap<>();
            // 计算Bonus
            // do sth to count the bonus
            List<Participate> participateList = iParticipateService.getPlayerInTournament(tournamentId);

            double totalBonus = 0;
            for (Participate participate: participateList) {
                if (participate.getIsDisqualified() == 1) continue;
                long playerId = participate.getPlayerId();
                double cspr = getPlayer(playerId).getRating();
                oldCsprMap.put(playerId, cspr);
                double bonus = 0;
                if (cspr < 1500) {
                    bonus += 1;
                } else {
                    bonus += 1;
                }
            }

            for (Participate participate: participateList) {
                if (participate.getIsDisqualified() == 1) continue;
                Rating player = getPlayer(participate.getPlayerId());
                double cspr = player.getRating();
                // player.setRating(cspr + totalBonus);
            }

            // 获取比赛的set列表
            List<com.cnsmash.cspr.api.entity.Set> setList = iSetService.getSetListByTournament(tournamentId);

            for (Set set: setList) {
                // set有人DQ则该set跳过
                if (set.getDq() == 1) continue;

                Long player1Id = set.getPlayer1Id();
                Long player2Id = set.getPlayer2Id();
                Long winnerId = set.getWinnerId();
                // player1 WIN
                if (winnerId == player1Id) {
                    results.addResult(getPlayer(player1Id), getPlayer(player2Id));
                } else if (winnerId == player2Id) {
                    results.addResult(getPlayer(player2Id), getPlayer(player1Id));
                }
                // ratingSystem.updateRatings(results);
            }
            ratingSystem.updateRatings(results);

            // 计算后的结果填入数据库
            for (Participate participate: participateList) {
                Log log = new Log();
                long playerId = participate.getPlayerId();
                String logId = String.valueOf(playerId) + "-" + String.valueOf(tournamentId);
                log.setLogId(logId);
                log.setPlayerId(playerId);
                log.setTournamentId(tournamentId);
                log.setOldCspr((int) Math.round(oldCsprMap.get(playerId)));
                log.setNewCspr((int) Math.round(getPlayer(playerId).getRating()));
                log.setStanding(participate.getStanding());
                iLogService.saveOrUpdate(log);
            }

        }

    }

}
