package com.cnsmash.cspr.backend.controller;

import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.backend.smashgg.script.TournamentFetch;
import com.cnsmash.cspr.framework.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 后台操作服务接口
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/cspr/backend")
public class BackendController {

    @RequestMapping("/updateTournamentDetail/{tournamentId}")
    @ResponseBody
    public ApiResult<String> updateTournamentDetail(@PathVariable long tournamentId) {
        TournamentFetch.getTournamentDetail(tournamentId);
        return ApiResult.ok("已将更新操作发送后台");
    }

}
