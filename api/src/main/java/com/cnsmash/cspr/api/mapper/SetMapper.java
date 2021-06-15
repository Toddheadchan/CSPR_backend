package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.vo.PlayerSet;
import com.cnsmash.cspr.api.entity.Set;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Mapper
public interface SetMapper extends BaseMapper<Set> {

    /**
     * 根据playerId与tournamentId获取所有sets
     * @param playerId
     * @param tournamentId
     * @return 对应的sets列表
     */
    public List<PlayerSet> getSetsByPlayerAndTournamentUnOrder(long playerId, long tournamentId);

    public Set queryById(long setId);

    public List<PlayerSet> getSetsByPlayer(long playerId);

    public void updatePlayerId(long subPlayerId, long mainPlayerId);

}
