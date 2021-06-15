package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-18
 */
public interface IBannerService extends IService<Banner> {

    /**
     * 获取当前能够显示banner图片列表
     * @return 图片信息列表
     */
    public List<Banner> getCurrentBannerList();

}
