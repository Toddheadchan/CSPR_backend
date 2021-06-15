package com.cnsmash.cspr.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("选手角度比赛结构")
@Data
public class PlayerTournament {

    @ApiModelProperty("比赛ID")
    private int tournamentId;

    @ApiModelProperty("选手ID")
    private int playerId;

    @ApiModelProperty("比赛类型")
    private int type;

    @ApiModelProperty("日期")
    private LocalDateTime date;

    @ApiModelProperty("league名称")
    private String leagueName;

    @ApiModelProperty("比赛名称")
    private String name;

    @ApiModelProperty("参加人数")
    private int entrants;

    @ApiModelProperty("比赛成绩")
    private int standing;

    @ApiModelProperty("是否DQ")
    private int isDisqualified;

    @ApiModelProperty("set列表")
    private List<PlayerSet> sets;

}
