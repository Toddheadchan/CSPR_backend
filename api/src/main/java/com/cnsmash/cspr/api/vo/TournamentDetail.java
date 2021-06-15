package com.cnsmash.cspr.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TournamentDetail {

    private Long tournamentId;

    private Long leagueId;

    private String leagueName;

    private String slug;

    private Integer type;

    private String name;

    private LocalDateTime date;

    private Integer season;

    private Integer status;

    private Integer entrants;

    private Integer csprRate;

    private Integer csprRank;

    private Integer online;

    private String displayResult;

    private String stream;

    private String vod;

    private Long regionId;

    private String logo;

}
