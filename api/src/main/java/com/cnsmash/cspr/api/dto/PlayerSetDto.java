package com.cnsmash.cspr.api.dto;

import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.vo.PlayerGame;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("玩家角度大局结构")
@Data
public class PlayerSetDto {

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

    @ApiModelProperty("比赛结果，1:胜利 | 0:败北")
    private int result;

    @ApiModelProperty("是否DQ，1:有DQ | 0:无DQ")
    private int dq;

    @ApiModelProperty("game列表")
    private List<PlayerGame> games;

    /**
     * 根据set实体组装选手角度的set结构
     * @param set set实体
     * @param myId 视角选手ID
     */
    public static PlayerSetDto setRealizer(Set set, int myId) {
        PlayerSetDto setInfo = new PlayerSetDto();
        return setInfo;
    }

}
