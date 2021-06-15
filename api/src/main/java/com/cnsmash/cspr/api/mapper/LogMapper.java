package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.cspr.api.vo.PlayerCsprLog;
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
public interface LogMapper extends BaseMapper<Log> {

    public List<PlayerCsprLog> getCsprLog(int playerId, int season);

}
