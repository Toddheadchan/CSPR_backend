package com.cnsmash.cspr.api.vo;

import com.cnsmash.cspr.framework.dto.KeyValueDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("角色对局数据")
@Data
public class CharacterMatchUp {

    @ApiModelProperty("最多使用")
    private KeyValueDto<String, String> mostPlayed;

    @ApiModelProperty("获胜最多")
    private KeyValueDto<String, String> mostWin;

    @ApiModelProperty("败北最多")
    private KeyValueDto<String, String> mostLose;

}
