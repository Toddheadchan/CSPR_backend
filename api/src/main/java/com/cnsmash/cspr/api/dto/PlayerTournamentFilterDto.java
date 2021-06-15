package com.cnsmash.cspr.api.dto;

import com.cnsmash.cspr.framework.dto.OrderFilterDto;
import com.cnsmash.cspr.framework.dto.PaginationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("选手页面查询比赛筛选器")
@Data
public class PlayerTournamentFilterDto {

    @ApiModelProperty("玩家ID")
    private int playerId;

    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("分页器")
    private PaginationDto pagination;

    @ApiModelProperty("排序结构")
    private OrderFilterDto orderFilter;

}
