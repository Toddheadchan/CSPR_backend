package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.dto.PlayerTournamentFilterDto;
import com.cnsmash.cspr.api.dto.TournamentFilterDto;
import com.cnsmash.cspr.api.dto.UpdateTournamentDto;
import com.cnsmash.cspr.api.entity.Tournament;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cnsmash.cspr.api.vo.PlayerTournament;
import com.cnsmash.cspr.api.vo.TournamentDetail;
import com.cnsmash.cspr.api.vo.TournamentStanding;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface ITournamentService extends IService<Tournament> {

    /**
     * 获取最近10天内比赛结果列表（已结束的）
     * 限量25条
     * @return 比赛结果列表
     */
    public List<TournamentDetail> getRecentTournament();

    /**
     * 根据筛选条件获取选手页面比赛列表
     * @param playerTournamentFilterDto 筛选条件
     * @return 比赛列表
     */
    public List<PlayerTournament> getPlayerTournamentList(PlayerTournamentFilterDto playerTournamentFilterDto);

    /**
     * 将比赛展示结果添加到数据库
     * @param tournamentId 比赛ID
     * @param displayResultStr 显示结果
     */
    public void setDisplayResult(Long tournamentId, String displayResultStr);

    /**
     * 根据条件获取比赛列表
     * @param tournamentFilterDto 筛选条件
     * @return 比赛列表
     */
    public List<TournamentDetail> getTournamentList(TournamentFilterDto tournamentFilterDto);

    /**
     * 根据ID获取比赛详细信息
     * @param tournamentId 比赛ID
     * @return 比赛详细信息
     */
    public TournamentDetail getTournamentDetail(long tournamentId);

    /**
     * 获取比赛排名数据（分页）
     * @param tournamentId 比赛ID
     * @return 比赛排名列表
     */
    public List<TournamentStanding> getTournamentStanding(long tournamentId);

    /**
     * 根据赛季获取比赛列表（全部）
     * @param season 赛季编号
     * @return 比赛列表
     */
    public List<Tournament> getTournamentBySeason(int season);


    /**
     * 轻量级比赛信息更新
     * @param updateTournamentDto 更新数据结构
     */
    public void updateTournamentLite(UpdateTournamentDto updateTournamentDto);
}
