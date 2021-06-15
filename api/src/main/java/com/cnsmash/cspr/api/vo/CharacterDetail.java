package com.cnsmash.cspr.api.vo;

import com.cnsmash.cspr.api.dto.CharacterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("玩家角色数据结构")
@Data
public class CharacterDetail {

    @ApiModelProperty("胜利次数")
    private int win;

    @ApiModelProperty("败北次数")
    private int lose;

    @ApiModelProperty("solo次数")
    private int solo;

    @ApiModelProperty("starter次数")
    private int starter;

    @ApiModelProperty("counter次数")
    private int counter;

    @ApiModelProperty("drop次数")
    private int drop;

    @ApiModelProperty("角色对局数据")
    private CharacterMatchUp matchUp;

    @ApiModelProperty("角色场地数据")
    private CharacterStage stage;

    public CharacterDetail(CharacterDto characterDto) {
        this.win = characterDto.getWin();
        this.lose = characterDto.getLose();
        this.solo = characterDto.getSolo();
        this.starter = characterDto.getStarter();
        this.counter = characterDto.getCounter();
        this.drop = characterDto.getDrop();
    }

}
