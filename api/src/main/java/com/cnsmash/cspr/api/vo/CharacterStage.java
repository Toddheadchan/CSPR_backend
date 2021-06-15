package com.cnsmash.cspr.api.vo;

import com.cnsmash.cspr.framework.dto.KeyValueDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("角色场地数据")
@Data
public class CharacterStage {

    @ApiModelProperty("最多starter场地")
    private KeyValueDto<String, String> starter;

    @ApiModelProperty("最多counter场地")
    private KeyValueDto<String, String> counter;

    @ApiModelProperty("最多胜利场地")
    private KeyValueDto<String, String> mostWin;

    @ApiModelProperty("最多失利场地")
    private KeyValueDto<String, String> mostLose;

}
