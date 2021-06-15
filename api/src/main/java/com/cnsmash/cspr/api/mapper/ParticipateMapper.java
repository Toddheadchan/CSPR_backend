package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Participate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Mapper
public interface ParticipateMapper extends BaseMapper<Participate> {

    public void updatePlayerId(long subPlayerId, long mainPlayerId);

}
