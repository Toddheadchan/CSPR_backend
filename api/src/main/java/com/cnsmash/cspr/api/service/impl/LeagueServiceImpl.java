package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.League;
import com.cnsmash.cspr.api.mapper.GameMapper;
import com.cnsmash.cspr.api.mapper.LeagueMapper;
import com.cnsmash.cspr.api.service.ILeagueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-30
 */
@Service
public class LeagueServiceImpl extends ServiceImpl<LeagueMapper, League> implements ILeagueService {

    @Autowired
    LeagueMapper leagueMapper;

}
