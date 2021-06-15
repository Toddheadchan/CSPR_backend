package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Game;
import com.cnsmash.cspr.api.mapper.GameMapper;
import com.cnsmash.cspr.api.service.IGameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.vo.PlayerGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements IGameService {

    @Autowired
    GameMapper gameMapper;

    @Override
    public List<PlayerGame> getPlayerGameDetail(long playerId, long setId) {
        List<PlayerGame> playerGames = gameMapper.getGamesByPlayerAndSetUnOrder(playerId, setId);
        for (PlayerGame game: playerGames) {
            game.gameAdapter(playerId);
        }
        return playerGames;
    }

    @Override
    public List<Game> getGameBySetId(Long setId) {
        return gameMapper.getGameBySetId(setId);
    }

}
