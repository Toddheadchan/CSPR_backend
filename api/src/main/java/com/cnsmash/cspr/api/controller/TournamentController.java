package com.cnsmash.cspr.api.controller;


import com.cnsmash.cspr.api.dto.TournamentFilterDto;
import com.cnsmash.cspr.api.dto.UpdateTournamentDto;
import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.api.vo.TournamentDetail;
import com.cnsmash.cspr.api.vo.TournamentStanding;
import com.cnsmash.cspr.framework.api.ApiResult;
import com.cnsmash.cspr.framework.vo.PaginationVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/cspr/tournament")
public class TournamentController {

    @Autowired
    ITournamentService iTournamentService;

    @RequestMapping("/recent")
    @ResponseBody
    public ApiResult<List<TournamentDetail> > getRecentTournament() {
        List<TournamentDetail> tournamentList = iTournamentService.getRecentTournament();
        ApiResult<List<TournamentDetail> > result = new ApiResult<>();
        return result.ok(tournamentList);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ApiResult<PaginationVo<TournamentDetail> > getTournamentList(@RequestBody TournamentFilterDto tournamentFilterDto) {
        List<TournamentDetail> tournamentList = iTournamentService.getTournamentList(tournamentFilterDto);
        PaginationVo<TournamentDetail> pagination = PaginationVo.getPagination(new PageInfo<>(tournamentList));
        ApiResult<PaginationVo<TournamentDetail>> result = new ApiResult<>();
        return result.ok(pagination);
    }

    @GetMapping("/detail/{tournamentId}")
    @ResponseBody
    public ApiResult<TournamentDetail> getTournamentDetail(@PathVariable long tournamentId) {
        TournamentDetail tournamentDetail = iTournamentService.getTournamentDetail(tournamentId);
        ApiResult<TournamentDetail> result = new ApiResult<>();
        return result.ok(tournamentDetail);
    }

    @GetMapping("/standing/{tournamentId}/{page}")
    @ResponseBody
    public ApiResult<PaginationVo<TournamentStanding>> getTournamentStanding(@PathVariable long tournamentId, @PathVariable int page) {
        List<TournamentStanding> tournamentStanding = iTournamentService.getTournamentStanding(tournamentId);
        PaginationVo<TournamentStanding> pagination = new PaginationVo(tournamentStanding.size(), 8, tournamentStanding.subList((page - 1) * 8, Integer.min(page * 8, tournamentStanding.size() - 1)));
        ApiResult<PaginationVo<TournamentStanding>> result = new ApiResult<>();
        return result.ok(pagination);
    }

    @PostMapping("/update/lite")
    @ResponseBody
    public ApiResult<String> updateTournamentLite(@RequestBody UpdateTournamentDto updateTournamentDto) {
        iTournamentService.updateTournamentLite(updateTournamentDto);
        return ApiResult.ok("已更新比赛信息");
    }
}

