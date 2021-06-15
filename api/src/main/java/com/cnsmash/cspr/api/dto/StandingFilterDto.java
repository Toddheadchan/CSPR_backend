package com.cnsmash.cspr.api.dto;

import com.cnsmash.cspr.framework.dto.PaginationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("比赛排名筛选器结构")
public class StandingFilterDto {

    @ApiModelProperty("比赛ID")
    private Long tournamentId;

    @ApiModelProperty("分页信息")
    private PaginationDto pagination;

}
