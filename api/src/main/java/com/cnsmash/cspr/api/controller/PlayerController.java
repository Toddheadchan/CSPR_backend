package com.cnsmash.cspr.api.controller;


import com.cnsmash.cspr.api.dto.PlayerListFilterDto;
import com.cnsmash.cspr.api.dto.PlayerTournamentFilterDto;
import com.cnsmash.cspr.api.entity.Player;
import com.cnsmash.cspr.api.entity.Stats;
import com.cnsmash.cspr.api.service.ILogService;
import com.cnsmash.cspr.api.service.IPlayerService;
import com.cnsmash.cspr.api.service.IStatsService;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.api.vo.PlayerCsprLog;
import com.cnsmash.cspr.api.vo.PlayerInfo;
import com.cnsmash.cspr.api.vo.PlayerTournament;
import com.cnsmash.cspr.framework.api.ApiResult;
import com.cnsmash.cspr.framework.vo.PaginationVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/cspr/player")
public class PlayerController {

    @Autowired
    IPlayerService iPlayerService;

    @Autowired
    ITournamentService iTournamentService;

    @Autowired
    ILogService iLogService;

    @Autowired
    IStatsService iStatsService;

    @PostMapping("/list")
    @ResponseBody
    public ApiResult<PaginationVo<PlayerInfo> > getPlayerList(@RequestBody PlayerListFilterDto playerListFilterDto) {
        List<PlayerInfo> playerList = iPlayerService.getPlayerList(playerListFilterDto);
        PaginationVo<PlayerInfo> pagination = PaginationVo.getPagination(new PageInfo<>(playerList));
        ApiResult<PaginationVo<PlayerInfo> > result = new ApiResult<>();
        return result.ok(pagination);
    }

    @GetMapping("/info/{playerId}")
    @ResponseBody
    public ApiResult<PlayerInfo> getPlayerInfo(@PathVariable("playerId") int playerId) {
        PlayerInfo playerInfo = iPlayerService.getPlayerInfo(playerId);
        ApiResult<PlayerInfo> result = new ApiResult<>();
        return result.ok(playerInfo);
    }

    @GetMapping("/stats/{playerId}")
    @ResponseBody
    public ApiResult<Stats> getPlayerStats(@PathVariable("playerId") int playerId) {
        Stats stats = iStatsService.getById(playerId);
        return ApiResult.ok(stats);
    }

    @PostMapping("/tournament")
    @ResponseBody
    public ApiResult<PaginationVo<PlayerTournament> > getPlayerTournament(@RequestBody PlayerTournamentFilterDto playerTournamentFilterDto) {
        List<PlayerTournament> tournamentList = iTournamentService.getPlayerTournamentList(playerTournamentFilterDto);
        PaginationVo<PlayerTournament> pagination = PaginationVo.getPagination(new PageInfo<>(tournamentList));
        ApiResult<PaginationVo<PlayerTournament> > result = new ApiResult<>();
        return result.ok(pagination);
    }

    @GetMapping("/cspr/{playerId}/{season}")
    @ResponseBody
    public ApiResult<List<PlayerCsprLog> > getPlayerCspr(@PathVariable("playerId") int playerId, @PathVariable("season") int season) {
        List<PlayerCsprLog> csprLog = iLogService.getCsprLog(playerId, season);
        ApiResult<List<PlayerCsprLog> > result = new ApiResult<>();
        return result.ok(csprLog);
    }

    @GetMapping("/attach/{subPlayerId}/{mainPlayerId}")
    @ResponseBody
    public ApiResult<String> attachPlayerId(@PathVariable long subPlayerId, @PathVariable long mainPlayerId) {
        iPlayerService.attachPlayerId(subPlayerId, mainPlayerId);
        return ApiResult.ok("已合并两个账号");
    }

}

