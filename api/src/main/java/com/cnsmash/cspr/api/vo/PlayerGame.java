package com.cnsmash.cspr.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("选手角度小局结构")
@Data
public class PlayerGame {

    @ApiModelProperty("小局ID")
    private int gameId;

    @ApiModelProperty("小局序号")
    private int gameIndex;

    @ApiModelProperty("自身ID")
    private int myId;

    @ApiModelProperty("自身分数，-1表示没有数据")
    private int myStock;

    @ApiModelProperty("自身选择角色")
    private String myCharacter;

    @ApiModelProperty("对手ID")
    private int oppoId;

    @ApiModelProperty("对手分数，-1表示没有数据")
    private int oppoStock;

    @ApiModelProperty("对手选择角色")
    private String oppoCharacter;

    @ApiModelProperty("场地")
    private String stage;

    @ApiModelProperty("小分结果，1:胜利|0:败北")
    private int result;

    public void gameAdapter(long playerId) {
        if (myId != playerId) {
            int tId = myId; myId = oppoId; oppoId = tId;
            int tStock = myStock; myStock = oppoStock; oppoStock = tStock;
            String tCharacter = myCharacter; myCharacter = oppoCharacter; oppoCharacter = tCharacter;
            result = 1 - result;
        }
    }

}
