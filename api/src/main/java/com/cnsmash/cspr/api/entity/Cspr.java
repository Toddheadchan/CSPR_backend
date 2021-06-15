package com.cnsmash.cspr.api.entity;

import java.io.Serializable;

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

    private Long playerId;

    private Integer season;

    private Integer cspr;

    public Cspr() {}

    public Cspr(long playerId) {
        this.playerId = playerId;
        this.season = SeasonUtil.getCurrentSeason();
        this.cspr = 1500;
    }

    public Cspr(long playerId, Integer cspr) {
        this.playerId = playerId;
        this.season = SeasonUtil.getCurrentSeason();
        this.cspr = cspr;
    }

}
