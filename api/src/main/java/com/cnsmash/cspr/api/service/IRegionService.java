package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface IRegionService extends IService<Region> {

    /**
     * 获取所有地区列表
     * @return 地区列表数组
     */
    public List<Region> getRegionList();

}
