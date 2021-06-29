package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateTournamentDto {

    @ApiModelProperty("比赛ID")
    private Long tournamentId;

    @ApiModelProperty("计入cspr")
    private Integer csprRate;

    @ApiModelProperty("是否线上")
    private Integer online;

    @ApiModelProperty("回放地址")
    private String vod;

}
