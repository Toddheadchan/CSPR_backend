package com.cnsmash.cspr.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
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
public class Cspr implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cspr_id")
    private String csprId;

    private Long playerId;

    private Integer season;

    private Integer cspr;

    private Integer ranking;

    public Cspr() {}

    public Cspr(long playerId) {
        this.playerId = playerId;
        this.season = SeasonUtil.getCurrentSeason();
        this.csprId = playerId + "-" + this.season;
        this.cspr = 1500;
    }

    public Cspr(long playerId, Integer season, Integer cspr, Integer ranking) {
        this.csprId = playerId + "-" + season;
        this.playerId = playerId;
        this.season = season;
        this.cspr = cspr;
        this.ranking = ranking;
    }

}
