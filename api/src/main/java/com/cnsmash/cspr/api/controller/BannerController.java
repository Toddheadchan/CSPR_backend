package com.cnsmash.cspr.api.controller;


import com.cnsmash.cspr.api.entity.Banner;
import com.cnsmash.cspr.api.service.IBannerService;
import com.cnsmash.cspr.framework.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Amaki
 * @since 2021-04-18
 */
@CrossOrigin
@RestController
@RequestMapping("/cspr/banner")
public class BannerController {

    @Autowired
    IBannerService iBannerService;

    @GetMapping("/list")
    @ResponseBody
    public ApiResult<List<Banner> > getBannerList() {
        List<Banner> bannerList = iBannerService.getCurrentBannerList();
        ApiResult<List<Banner> > result = new ApiResult<>();
        return result.ok(bannerList);
    }

}