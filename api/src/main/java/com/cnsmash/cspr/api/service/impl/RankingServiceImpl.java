package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.dto.RankingDto;
import com.cnsmash.cspr.api.mapper.CsprMapper;
import com.cnsmash.cspr.api.service.IRankingService;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements IRankingService {

    @Autowired
    CsprMapper csprMapper;

    /**
     * 获取排名列表
     * @param regionId 地区ID，0表示所有地区
     * @param limit 限制数量，0表示不限数量
     * @param season 赛季编号，-1则取当前赛季
     * @return
     */
    @Override
    public List<RankingDto> getRegionRanking(int regionId, int limit, int season) {
        int querySeason = season == -1 ? SeasonUtil.getCurrentSeason() : season;
        return csprMapper.getRegionRanking(regionId, limit, querySeason);
    }

}
