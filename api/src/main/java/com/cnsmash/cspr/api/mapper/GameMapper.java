package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Game;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.cspr.api.vo.PlayerGame;
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
public interface GameMapper extends BaseMapper<Game> {

    public List<PlayerGame> getGamesByPlayerAndSetUnOrder(long playerId, long setId);

    public List<Game> getGameBySetId(Long setId);

}
