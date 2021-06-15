package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Log;
import com.cnsmash.cspr.api.mapper.LogMapper;
import com.cnsmash.cspr.api.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.vo.PlayerCsprLog;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public List<PlayerCsprLog> getCsprLog(int playerId, int season) {
        List<PlayerCsprLog> csprLog = logMapper.getCsprLog(playerId, season);
        for (PlayerCsprLog log: csprLog) {
            if (log.getTournamentName() == null) {
                log.setTournamentName("赛季更新分数重置");
                log.setDate(SeasonUtil.getSeasonStartDate(season));
            }
        }
        return csprLog;
    }

}
