package com.cnsmash.cspr.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("玩家角度大局结构")
@Data
public class PlayerSet {

    @ApiModelProperty("setID")
    private int setId;

    @ApiModelProperty("自身ID")
    private int myId;

    @ApiModelProperty("自身tag")
    private String myTag;

    @ApiModelProperty("自身分数")
    private int myScore;

    @ApiModelProperty("对手ID")
    private int oppoId;

    @ApiModelProperty("对手tag")
    private String oppoTag;

    @ApiModelProperty("对手分数")
    private int oppoScore;

    @ApiModelProperty("轮次名称")
    private String fullRoundText;

    @ApiModelProperty("比赛结果，1:胜利 | 0:败北")
    private int result;

    @ApiModelProperty("是否DQ，1:有DQ | 0:无DQ")
    private int dq;

    @ApiModelProperty("game列表")
    private List<PlayerGame> games;


    public void setAdapter(long playerId) {
        if (myId != playerId) {
            int tId = myId; myId = oppoId; oppoId = tId;
            String tTag = myTag; myTag = oppoTag; oppoTag = tTag;
            int tScore = myScore; myScore = oppoScore; oppoScore = tScore;
        }
        if (myScore > oppoScore) result = 1;
        else result = 0;
    }

}
