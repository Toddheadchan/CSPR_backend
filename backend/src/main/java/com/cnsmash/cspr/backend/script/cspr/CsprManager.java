package com.cnsmash.cspr.backend.script.cspr;

import com.cnsmash.cspr.api.entity.*;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.service.*;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.api.vo.TournamentStanding;
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

    @Resource
    ICsprService iCsprService = (ICsprService) ApiSpringUtil.getBean(ICsprService.class);

    private Map<Long, Rating> playerMap;

    private RatingCalculator ratingSystem;

    private RatingPeriodResults results;

    private List<Log> logList;

    private int season;

    // 新赛季成绩重置
    private void newSeasonReset(int season) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("season", season - 1);
        List<Cspr> csprList = iCsprService.listByMap(queryMap);
        for (Cspr cspr: csprList) {
            Rating player = new Rating(String.valueOf(cspr.getPlayerId()), this.ratingSystem);
            double newRating = 0.25 * (cspr.getCspr() - 1500) + 1500;
            player.setRating(newRating);
            player.setRatingDeviation(30);
            this.playerMap.put(cspr.getPlayerId(), player);
            Log resetLog = new Log(cspr.getPlayerId(), -season, cspr.getCspr(), (int)newRating, -1);
            iLogService.saveOrUpdate(resetLog);
        }
    }

    private Rating getPlayer(long playerId) {
        if (this.playerMap.keySet().contains(playerId) == false) {
            Rating player = new Rating(String.valueOf(playerId), this.ratingSystem);
            player.setRating(1500);
            player.setRatingDeviation(30);
            playerMap.put(playerId, player);
            Log newLog = new Log(playerId, -this.season, 1500, 1500, -1);
            iLogService.saveOrUpdate(newLog);
        }
        return playerMap.get(playerId);
    }

    public void calculate(int season) {

        this.season = season;

        this.playerMap = new HashMap<>();
        this.ratingSystem = new RatingCalculator(0.06, 0.5);
        this.logList = new LinkedList<>();

        // 若不是计算第0赛季，则获取选手上一赛季的成绩作新赛季成绩预设
        if (season != 0) {
            newSeasonReset(season);
        }

        // 获取赛季对应比赛列表
        List<Tournament> tournamentList = iTournamentService.getTournamentBySeason(season);

        for (Tournament tournament: tournamentList) {

            System.out.println("======================");
            System.out.println("tournament : " + tournament.getName());

            // 重置结果集
            this.results = new RatingPeriodResults();

            long tournamentId = tournament.getTournamentId();
            Map<Long, Double> oldCsprMap = new HashMap<>();
            // 计算Bonus
            List<Participate> participateList = iParticipateService.getPlayerInTournament(tournamentId);

            // SPR用map
            Map<Integer, Integer> sprMap = new HashMap<>();
            int preSpr = 0, sprCount = 0;

            double totalBonus = 0;
            for (Participate participate: participateList) {
                // SPR构建
                int standing = participate.getStanding();
                if (standing != preSpr) {
                    sprCount += 1;
                    preSpr = standing;
                    sprMap.put(preSpr, sprCount);
                }
                if (participate.getIsDisqualified() == 1) continue;
                long playerId = participate.getPlayerId();
                double cspr = getPlayer(playerId).getRating();
                oldCsprMap.put(playerId, cspr);
                double bonus;
                if (cspr > 1500) {
                    // 高于1500的选手bonus
                    bonus = Math.pow(2, (cspr - 1500) / 173.7178);
                } else {
                    bonus = 1;
                }
                //System.out.println("player cspr: " + cspr + ", bonus: " + bonus);
                totalBonus += bonus;
            }

            System.out.println("total bonus: " + totalBonus + ", total player: " + participateList.size());

            /********** bonus计算关键参数 **********/
            double a = 0.8;
            double b = 0.2;
            double bonusRate = Math.sqrt(Math.log(participateList.size()) / Math.log(8));
            double avgPlayerCount = Math.floor(0.6 * participateList.size());

            for (Participate participate: participateList) {
                if (participate.getIsDisqualified() == 1) continue;
                Rating player = getPlayer(participate.getPlayerId());
                int standing = participate.getStanding();
                double cspr = player.getRating();
                // double rankingRate = a / sprMap.get(standing) + Math.max(0, b * (avgPlayerCount - standing) / 2 / avgPlayerCount);
                double rankingRate = a / sprMap.get(standing) + b * (avgPlayerCount - standing) / 2 / avgPlayerCount;
                double playerBonus =  bonusRate * rankingRate * (totalBonus - (cspr > 1500 ? Math.pow(2, (cspr - 1500) / 173.7178) : 1));
                player.setRating(cspr + playerBonus);

                // rd修正
                player.RDRateCorrection(bonusRate, true);
            }

            System.out.println("bonus rate: " + bonusRate);

            // 获取比赛的set列表
            List<com.cnsmash.cspr.api.entity.Set> setList = iSetService.getSetListByTournament(tournamentId);

            for (Set set: setList) {
                // set有人DQ则该set跳过
                if (set.getDq() == 1) continue;

                Long player1Id = set.getPlayer1Id();
                Long player2Id = set.getPlayer2Id();
                Long winnerId = set.getWinnerId();
                // player1 WIN
                if (winnerId.equals(player1Id)) {
                    this.results.addResult(getPlayer(player1Id), getPlayer(player2Id));
                } else if (winnerId.equals(player2Id)) {
                    this.results.addResult(getPlayer(player2Id), getPlayer(player1Id));
                }
                // ratingSystem.updateRatings(results);
            }

            ratingSystem.updateRatings(this.results);

            // 计算后的结果填入数据库
            for (Participate participate: participateList) {
                if (participate.getIsDisqualified() == 1) continue;
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

                // rd恢复
                Rating player = getPlayer(participate.getPlayerId());
                player.RDRateCorrection(bonusRate, false);
            }

        }

        // 计算出结果后，更新cspr表
        List<Rating> csprList = new LinkedList<>();
        for (Long playerId: this.playerMap.keySet()) {
            csprList.add(this.playerMap.get(playerId));
        }
        Collections.sort(csprList, new Comparator<Rating>() {
            @Override
            public int compare(Rating rating1, Rating rating2) {
                if (rating1.getRating() < rating2.getRating()) return 1;
                else if (rating1.getRating() == rating2.getRating()) return 0;
                return -1;
            }
        });
        int ranking = 0;
        double preRating = -1;
        for (Rating rating: csprList) {
            ranking += 1;
            Cspr cspr = new Cspr(Long.valueOf(rating.getUid()), season, (int)rating.getRating(), ranking);
            iCsprService.saveOrUpdate(cspr);
        }

    }

}
