package com.cnsmash.cspr.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("简易比赛信息结构")
@Data
public class TournamentLiteDto {

    @ApiModelProperty("比赛名称")
    private String name;

    @ApiModelProperty("比赛logo")
    private String logo;

    @ApiModelProperty("日期")
    private LocalDateTime date;

    @ApiModelProperty("参赛人数")
    private int entrants;

    @ApiModelProperty("是否计入CSPR，0：不计入|1：计入")
    private int csprRate;

    @ApiModelProperty("CSPR比赛评级")
    private int csprRank;

    @ApiModelProperty("地区ID")
    private int regionId;

    @ApiModelProperty("是否线上，0：线下|1：线上")
    private int online;

}
