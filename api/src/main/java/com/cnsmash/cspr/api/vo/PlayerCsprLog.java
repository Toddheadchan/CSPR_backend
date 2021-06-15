package com.cnsmash.cspr.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("选手CSPR表格数据结构")
@Data
public class PlayerCsprLog {

    @ApiModelProperty("日期")
    private LocalDateTime date;

    @ApiModelProperty("CSPR分数")
    private Integer cspr;

    @ApiModelProperty("比赛前的CSPR分数")
    private Integer lastCspr;

    @ApiModelProperty("比赛名称")
    private String tournamentName;

    @ApiModelProperty("比赛排名")
    private int standing;

    @ApiModelProperty("比赛人数")
    private int entrants;

}
