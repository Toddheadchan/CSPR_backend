package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.dto.PlayerTournamentFilterDto;
import com.cnsmash.cspr.api.dto.TournamentFilterDto;
import com.cnsmash.cspr.api.dto.TournamentLiteDto;
import com.cnsmash.cspr.api.entity.Tournament;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.cspr.api.vo.PlayerTournament;
import com.cnsmash.cspr.api.vo.TournamentDetail;
import com.cnsmash.cspr.api.vo.TournamentStanding;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
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
public interface TournamentMapper extends BaseMapper<Tournament> {

    /**
     * 根据时间段获取比赛列表
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return 在时间段内的比赛信息（简易数据结构）
     */
    public List<TournamentLiteDto> getTournamentLiteInTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取最近结束的比赛用于首页显示
     * @return 最近结束的比赛列表
     */
    public List<TournamentDetail> getRecentTournament(String ddl);

    /**
     * 获取选手页面比赛列表
     * @param playerTournamentFilterDto 筛选器
     * @return 对应列表（完整，使用Pagehelper分页）
     */
    public List<PlayerTournament> getPlayerTournamentList(PlayerTournamentFilterDto playerTournamentFilterDto);

    /**
     * 将比赛结果添加到数据库
     * @param tournamentId
     * @param displayResultStr
     */
    public void setDisplayResult(long tournamentId, String displayResultStr);

    /**
     * 根据筛选条件获取比赛列表
     * @param tournamentFilterDto 筛选器结构
     * @return 比赛详细信息列表
     */
    public List<TournamentDetail> getTournamentList(TournamentFilterDto tournamentFilterDto);

    /**
     * 根据比赛ID获取比赛详细信息
     * @param tournamentId 比赛ID
     * @return 比赛详细信息
     */
    public TournamentDetail getTournamentDetail(long tournamentId);

    /**
     * 根据比赛ID获取比赛排名详情
     * @param tournamentId 比赛ID
     * @return 排名详情列表
     */
    public List<TournamentStanding> getTournamentStanding(long tournamentId);

    /**
     * 根据赛季编号获取比赛列表
     * @param season 赛季编号
     * @return 对应比赛列表，按照比赛时间顺序
     */
    public List<Tournament> getTournamentBySeason(int season);

}
