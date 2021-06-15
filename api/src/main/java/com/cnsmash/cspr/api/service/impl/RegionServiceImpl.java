package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Region;
import com.cnsmash.cspr.api.mapper.RegionMapper;
import com.cnsmash.cspr.api.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> getRegionList() {
        return regionMapper.selectByMap(new HashMap<>());
    }

}
