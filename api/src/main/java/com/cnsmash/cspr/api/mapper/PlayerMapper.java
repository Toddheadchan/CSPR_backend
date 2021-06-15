package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.dto.PlayerListFilterDto;
import com.cnsmash.cspr.api.entity.Player;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.cspr.api.vo.PlayerInfo;
import com.cnsmash.cspr.api.vo.PlayerLite;
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
public interface PlayerMapper extends BaseMapper<Player> {

    public List<PlayerInfo> getPlayerList(PlayerListFilterDto playerListFilterDto);

    public PlayerLite getPlayerLite(long playerId);

}
