package com.cnsmash.cspr.api.entity;

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
public class Stats implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long playerId;

    private Integer tournamentCount;

    private Integer setWin;

    private Integer setLose;

    private Integer setCount;

    private Integer gameWin;

    private Integer gameLose;

    private Integer gameCount;

    private Integer lastTournamentId;

    public Stats() {}

    public Stats(long playerId) {
        this.playerId = playerId;
        this.tournamentCount = 0;
        this.setWin = 0;
        this.setLose = 0;
        this.setCount = 0;
        this.gameWin = 0;
        this.gameLose = 0;
        this.gameCount = 0;
        this.lastTournamentId = -1;
    }

}
