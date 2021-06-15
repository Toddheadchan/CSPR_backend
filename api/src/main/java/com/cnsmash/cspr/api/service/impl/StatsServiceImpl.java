package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.entity.Stats;
import com.cnsmash.cspr.api.mapper.StatsMapper;
import com.cnsmash.cspr.api.service.IStatsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Service
public class StatsServiceImpl extends ServiceImpl<StatsMapper, Stats> implements IStatsService {

    @Autowired
    StatsMapper statsMapper;

    @Override
    public void updateTournamentCount(long playerId, int tournamentCount) {
        statsMapper.updateTournamentCount(playerId, tournamentCount);
    }

    @Override
    public void loadSetStats(Set set) {
        long player1Id = set.getPlayer1Id();
        long player2Id = set.getPlayer2Id();
        int player1Score = set.getPlayer1Score();
        int player2Score = set.getPlayer2Score();
        statsMapper.updateBySetStats(player1Id, player1Score, player2Score);
        statsMapper.updateBySetStats(player2Id, player2Score, player1Score);
    }

    @Override
    public void updateLastTournament(long playerId) {
        statsMapper.updateLastTournament(playerId);
    }

}
