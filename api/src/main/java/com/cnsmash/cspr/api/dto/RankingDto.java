package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("排名单个结构")
@Data
public class RankingDto {

    @ApiModelProperty("玩家ID")
    private int playerId;

    @ApiModelProperty("玩家tag")
    private String playerTag;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("国籍")
    private String nation;

    @ApiModelProperty("地区")
    private int regionId;

    @ApiModelProperty("主main")
    private String mainCharacter;

    @ApiModelProperty("主main配色")
    private int mainCharacterColor;

    @ApiModelProperty("CSPR积分")
    private int cspr;
}
