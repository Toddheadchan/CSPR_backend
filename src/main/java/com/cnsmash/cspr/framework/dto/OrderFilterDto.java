package com.cnsmash.cspr.framework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("排序筛选器")
@Data
public class OrderFilterDto {

    @ApiModelProperty("列名")
    private String column;

    @ApiModelProperty("是否升序")
    private Boolean asc;

}
