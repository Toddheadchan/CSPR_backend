package com.cnsmash.cspr.api.controller;

import com.cnsmash.cspr.api.dto.RankingDto;
import com.cnsmash.cspr.api.service.IRankingService;
import com.cnsmash.cspr.framework.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cspr/ranking")
public class RankingController {

    @Autowired
    IRankingService iRankingService;

    @GetMapping("/region/{regionId}")
    @ResponseBody
    public ApiResult<List<RankingDto> > getRegionRanking(
            @PathVariable(value = "regionId") int regionId,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "season", defaultValue = "-1") int season) {
        List<RankingDto> rankingList = iRankingService.getRegionRanking(regionId, limit, season);
        ApiResult<List<RankingDto> > result = new ApiResult<>();
        return result.ok(rankingList);
    }
}
