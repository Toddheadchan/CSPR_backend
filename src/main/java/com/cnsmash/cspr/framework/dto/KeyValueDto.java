package com.cnsmash.cspr.framework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("键值对结构")
@Data
public class KeyValueDto<T, E> {

    @ApiModelProperty("键值")
    private T key;

    @ApiModelProperty("值")
    private E value;

    @ApiModelProperty("标签")
    private String label;

    public KeyValueDto (T key, E value) {
        this.key = key;
        this.value = value;
        this.label = "";
    }

    public KeyValueDto (T key, E value, String label) {
        this.key = key;
        this.value = value;
        this.label = label;
    }
}
