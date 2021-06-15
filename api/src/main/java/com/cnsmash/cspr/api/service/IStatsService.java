package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.entity.Stats;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface IStatsService extends IService<Stats> {

    public void updateTournamentCount(long playerId, int tournamentCount);

    public void loadSetStats(Set set);

    public void updateLastTournament(long playerId);

}
