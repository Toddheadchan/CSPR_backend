package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.dto.RankingDto;

import java.util.List;

public interface IRankingService {

    public List<RankingDto> getRegionRanking(int regionId, int limit, int season);

}
