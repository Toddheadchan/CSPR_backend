package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cnsmash.cspr.api.vo.PlayerGame;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface IGameService extends IService<Game> {

    public List<PlayerGame> getPlayerGameDetail(long playerId, long setId);

    public List<Game> getGameBySetId(Long setId);

}
