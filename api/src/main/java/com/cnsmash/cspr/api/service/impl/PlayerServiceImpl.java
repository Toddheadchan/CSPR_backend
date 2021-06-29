package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.dto.PlayerListFilterDto;
import com.cnsmash.cspr.api.dto.UpdatePlayerLiteDto;
import com.cnsmash.cspr.api.entity.*;
import com.cnsmash.cspr.api.mapper.*;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.cnsmash.cspr.api.service.IPlayerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.vo.PlayerInfo;
import com.cnsmash.cspr.api.vo.PlayerLite;
import com.cnsmash.cspr.framework.dto.PaginationDto;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements IPlayerService {

    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    StatsMapper statsMapper;

    @Autowired
    CsprMapper csprMapper;

    @Autowired
    LogMapper logMapper;

    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    IParticipateService iParticipateService;

    @Autowired
    ISetService iSetService;

    @Override
    public List<PlayerInfo> getPlayerList(PlayerListFilterDto playerListFilterDto) {
        PaginationDto pagination = playerListFilterDto.getPagination();
        PageHelper.startPage(pagination.getPageIndex(), pagination.getPageSize());
        return playerMapper.getPlayerList(playerListFilterDto);
    }

    @Override
    public PlayerInfo getPlayerInfo(int playerId) {
        return playerMapper.getPlayerInfo(playerId);
    }

    public void newPlayer(Player player) {
        playerMapper.insert(player);
        Stats stats = new Stats(player.getPlayerId());
        statsMapper.insert(stats);
        Cspr cspr = new Cspr(player.getPlayerId());
        csprMapper.insert(cspr);
        Log log = new Log(player.getPlayerId(), 0, 1500, 1500, -1);
        logMapper.insert(log);
    }

    @Override
    public PlayerLite getPlayerLite(long playerId) {
        return playerMapper.getPlayerLite(playerId);
    }

    @Override
    public void attachPlayerId(long subPlayerId, long mainPlayerId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("player_id", subPlayerId);
        iParticipateService.updatePlayerId(subPlayerId, mainPlayerId);
        iSetService.updatePlayerId(subPlayerId, mainPlayerId);
        statsMapper.deleteByMap(queryMap);
        characterMapper.deleteByMap(queryMap);
        logMapper.deleteByMap(queryMap);
        csprMapper.deleteByMap(queryMap);
        playerMapper.deleteByMap(queryMap);
        return;
    }

    @Override
    public void updatePlayerLite(UpdatePlayerLiteDto updatePlayerLiteDto) {
        Player player = playerMapper.selectById(updatePlayerLiteDto.getPlayerId());
        if (updatePlayerLiteDto.getRegionId() != null) {
            player.setRegionId(updatePlayerLiteDto.getRegionId());
        }
        if (updatePlayerLiteDto.getNation() != null) {
            player.setNation(updatePlayerLiteDto.getNation());
        }
        if (updatePlayerLiteDto.getMainCharacter() != null) {
            player.setMainCharacter(updatePlayerLiteDto.getMainCharacter());
        }
        if (updatePlayerLiteDto.getMainCharacterColor() != null) {
            player.setMainCharacterColor(updatePlayerLiteDto.getMainCharacterColor());
        }
        playerMapper.updateById(player);
    }

}
