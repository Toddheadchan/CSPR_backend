package com.cnsmash.cspr.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerInfo {

    private Integer playerId;

    private String playerTag;

    private String avatar;

    private String nation;

    private Integer regionId;

    private String mainCharacter;

    private Integer mainCharacterColor;

    private String mostCharacter;

    private Integer cspr;

    private Integer ranking;

    private String tournamentCount;

    private String setWin;

    private String setLose;

    private String setCount;

    private String gameWin;

    private String gameLose;

    private String gameCount;

    private LocalDateTime tournamentDate;

    private String tournamentName;

    private Integer tournamentStanding;

    private Integer tournamentEntrants;
}
