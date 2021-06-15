package com.cnsmash.cspr.api.dto;

import com.cnsmash.cspr.framework.dto.PaginationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("比赛列表筛选器结构")
public class TournamentFilterDto {

    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty("搜索地区ID")
    private Integer regionId;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("分页信息")
    private PaginationDto pagination;

}
