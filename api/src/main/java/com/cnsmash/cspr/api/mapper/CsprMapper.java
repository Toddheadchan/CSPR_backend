package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.dto.RankingDto;
import com.cnsmash.cspr.api.entity.Cspr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Mapper
public interface CsprMapper extends BaseMapper<Cspr> {

    /**
     * 获取排名列表
     * @param regionId 地区ID，0则为所有地区
     * @param limit 数量限制，0则为不限制
     * @param season 赛季
     * @return
     */
    public List<RankingDto> getRegionRanking(int regionId, int limit, int season);

}
