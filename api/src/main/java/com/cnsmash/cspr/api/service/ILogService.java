package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cnsmash.cspr.api.vo.PlayerCsprLog;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface ILogService extends IService<Log> {

    public List<PlayerCsprLog> getCsprLog(int playerId, int season);
}
