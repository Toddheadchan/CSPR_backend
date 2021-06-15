package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.entity.Participate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface IParticipateService extends IService<Participate> {

    public List<Participate> getPlayerInTournament(long tournamentId);

    public String countTournamentCharacter(long tournamentId, long playerId);

    public void updatePlayerId(long subPlayerId, long mainPlayerId);

}
