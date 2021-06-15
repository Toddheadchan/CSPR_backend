package com.cnsmash.cspr.api.controller;


import com.cnsmash.cspr.api.entity.Region;
import com.cnsmash.cspr.api.service.IRegionService;
import com.cnsmash.cspr.framework.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  地区模块控制器
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/cspr/region")
public class RegionController {

    @Autowired
    IRegionService iRegionService;

    @GetMapping("/list")
    @ResponseBody
    public ApiResult<List<Region> > getRegionList() {
        List<Region> regionList = iRegionService.getRegionList();
        ApiResult<List<Region> > result = new ApiResult<>();
        return result.ok(regionList);
    }
}

