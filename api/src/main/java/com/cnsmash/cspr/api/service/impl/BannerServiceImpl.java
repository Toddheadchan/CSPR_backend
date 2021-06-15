package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Banner;
import com.cnsmash.cspr.api.mapper.BannerMapper;
import com.cnsmash.cspr.api.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-18
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public List<Banner> getCurrentBannerList() {
        LocalDateTime now = LocalDateTime.now();
        return bannerMapper.getCurrentBannerList(now);
    }

}
