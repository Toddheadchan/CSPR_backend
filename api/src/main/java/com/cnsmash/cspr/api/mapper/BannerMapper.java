package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-18
 */

@Mapper
public interface BannerMapper extends BaseMapper<Banner> {

    public List<Banner> getCurrentBannerList(LocalDateTime now);

}
