package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.dto.PlayerListFilterDto;
import com.cnsmash.cspr.api.entity.Player;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cnsmash.cspr.api.vo.PlayerInfo;
import com.cnsmash.cspr.api.vo.PlayerLite;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface IPlayerService extends IService<Player> {

    public List<PlayerInfo> getPlayerList(PlayerListFilterDto playerListFilterDto);

    public Player getPlayerInfo(int playerId);

    public void newPlayer(Player player);

    public PlayerLite getPlayerLite(long playerId);

    public void attachPlayerId(long subPlayerId, long mainPlayerId);

}
