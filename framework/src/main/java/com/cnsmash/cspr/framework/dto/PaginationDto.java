package com.cnsmash.cspr.framework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分页结构")
@Data
public class PaginationDto {

    @ApiModelProperty("页码编号")
    private int pageIndex;

    @ApiModelProperty("页大小")
    private int pageSize;

}
