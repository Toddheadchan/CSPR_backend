package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdatePlayerLiteDto {

    @ApiModelProperty("选手ID")
    private Long playerId;

    @ApiModelProperty("所属地区ID")
    private Integer regionId;

    @ApiModelProperty("国籍")
    private String nation;

    @ApiModelProperty("main")
    private String mainCharacter;

    @ApiModelProperty("颜色")
    private Integer mainCharacterColor;

}
