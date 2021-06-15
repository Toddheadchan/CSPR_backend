package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Player;
import com.cnsmash.cspr.api.service.IGameService;
import com.cnsmash.cspr.api.vo.PlayerSet;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.mapper.SetMapper;
import com.cnsmash.cspr.api.service.ISetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class SetServiceImpl extends ServiceImpl<SetMapper, Set> implements ISetService {

    @Autowired
    SetMapper setMapper;

    @Autowired
    IGameService iGameService;

    /**
     * 获取选手角度的比赛列表
     * @param playerId 玩家ID
     * @param tournamentId 比赛ID
     * @return 选手角度的比赛列表
     */
    public List<PlayerSet> getPlayerSetDetail(long playerId, long tournamentId) {
        List<PlayerSet> playerSets = setMapper.getSetsByPlayerAndTournamentUnOrder(playerId, tournamentId);
        List<PlayerSet> playerSetsOrdered = new LinkedList<>();
        PlayerSet grandFinal = new PlayerSet();
        PlayerSet grandFinalReset = new PlayerSet();
        boolean hasGrandFinal = false, hasGrandFinalReset = false;
        for (PlayerSet set: playerSets) {
            set.setAdapter(playerId);
            set.setGames(iGameService.getPlayerGameDetail(playerId, set.getSetId()));
            String fullRoundText = set.getFullRoundText();
            if ("Grand Final".equals(fullRoundText)) {
                grandFinal = set;
                hasGrandFinal = true;
            } else if ("Grand Final Reset".equals(fullRoundText)) {
                grandFinalReset = set;
                hasGrandFinalReset = true;
            } else {
                playerSetsOrdered.add(set);
            }
        }
        if (hasGrandFinal) {
            playerSetsOrdered.add(grandFinal);
        }
        if (hasGrandFinalReset) {
            playerSetsOrdered.add(grandFinalReset);
        }
        return playerSetsOrdered;
    }

    public Set queryById(long setId) {
        return setMapper.queryById(setId);
    }

    public List<Set> getSetListByTournament(long tournamentId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("tournament_id", tournamentId);
        List<Set> setList = setMapper.selectByMap(queryMap);
        List<Set> finalList = new LinkedList<>();
        Set grandFinal = new Set();
        Set grandFinalReset = new Set();
        boolean hasGrandFinal = false, hasGrandFinalReset = false;
        for (Set set: setList) {
            String fullRoundText = set.getRoundText();
            if ("Grand Final".equals(fullRoundText)) {
                grandFinal = set;
                hasGrandFinal = true;
            } else if ("Grand Final Reset".equals(fullRoundText)) {
                grandFinalReset = set;
                hasGrandFinalReset = true;
            } else {
                finalList.add(set);
            }
        }
        if (hasGrandFinal) {
            finalList.add(grandFinal);
        }
        if (hasGrandFinalReset) {
            finalList.add(grandFinalReset);
        }
        return finalList;
    }

    public List<PlayerSet> getSetsByPlayer(long playerId) {
        List<PlayerSet> playerSets = setMapper.getSetsByPlayer(playerId);
        for (PlayerSet playerSet: playerSets) {
            playerSet.setAdapter(playerId);
            playerSet.setGames(iGameService.getPlayerGameDetail(playerId, playerSet.getSetId()));
        }
        return playerSets;
    }

    public void updatePlayerId(long subPlayerId, long mainPlayerId) {
        setMapper.updatePlayerId(subPlayerId, mainPlayerId);
    }

}
