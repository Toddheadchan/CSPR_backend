package com.cnsmash.cspr.api.dto;

import com.cnsmash.cspr.framework.dto.PaginationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("选手列表筛选器结构")
@Data
public class PlayerListFilterDto {

    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty("搜索地区ID")
    private Integer regionId;

    @ApiModelProperty("搜索使用角色")
    private String character;

    @ApiModelProperty("分页信息")
    private PaginationDto pagination;

}
