package com.cnsmash.cspr.backend.controller;

import com.cnsmash.cspr.backend.smashgg.script.TournamentFetch;
import com.cnsmash.cspr.framework.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResult<String> updateTournamentDetail(long tournamentId) {
        TournamentFetch.getTournamentDetail(tournamentId);
        return ApiResult.ok("已将更新操作发送后台");
    }

}
