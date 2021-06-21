package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Participate;
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
public interface ParticipateMapper extends BaseMapper<Participate> {

    public void updatePlayerId(long subPlayerId, long mainPlayerId);

    /**
     * 根据比赛ID获取参加者成绩名单
     * @param tournamentId 比赛ID
     * @return 参加者数据Participate结构
     */
    public List<Participate> getPlayerInTournament(long tournamentId);

}
