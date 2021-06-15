package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.vo.PlayerSet;
import com.cnsmash.cspr.api.entity.Set;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface ISetService extends IService<Set> {

    public List<PlayerSet> getPlayerSetDetail(long playerId, long tournamentId);

    public Set queryById(long setId);

    public List<Set> getSetListByTournament(long tournamentId);

    public List<PlayerSet> getSetsByPlayer(long playerId);

    public void updatePlayerId(long subPlayerId, long mainPlayerId);

}
