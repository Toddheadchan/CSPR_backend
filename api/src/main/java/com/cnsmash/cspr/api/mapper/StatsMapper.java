package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Stats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Mapper
public interface StatsMapper extends BaseMapper<Stats> {

    public void updateTournamentCount(long playerId, int tournamentCount);

    public void updateBySetStats(long playerId, int gameWin, int gameLose);

    public void setLastTournament(long playerId, long tournamentId);

    public void updateLastTournament(long playerId);

}
