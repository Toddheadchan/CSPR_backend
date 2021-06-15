package com.cnsmash.cspr.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tournament implements Serializable {

    private static final long serongialVersionUID = 1L;

    @TableId(value = "tournament_id")
    private Long tournamentId;

    private Long leagueId;

    private String slug;

    /**
     * 比赛类型（1：双败，2：RR，3：单挑，4：CB）
     */
    private Integer type;

    private String name;

    private LocalDateTime date;

    private Integer season;

    /**
     * 比赛状态（0：未开始，1：进行中，2：已完成）
     */
    private Integer status;

    private Integer entrants;

    private Integer csprRate;

    private Integer csprRank;

    private Integer online;

    private String displayResult;

    private String stream;

    private String vod;


}
