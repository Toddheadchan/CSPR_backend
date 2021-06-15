package com.cnsmash.cspr.api.dto;

import com.alibaba.fastjson.JSONObject;
import com.cnsmash.cspr.api.entity.Character;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("Character表格真正实体")
@Data
public class CharacterDto {

    @ApiModelProperty("玩家ID")
    private Long playerId;

    @ApiModelProperty("角色名称")
    private String character;

    @ApiModelProperty("总共胜利次数")
    private Integer win;

    @ApiModelProperty("总共失利次数")
    private Integer lose;

    @ApiModelProperty("solo次数")
    private Integer solo;

    @ApiModelProperty("starter次数")
    private Integer starter;

    @ApiModelProperty("counter次数")
    private Integer counter;

    @ApiModelProperty("drop次数")
    private Integer drop;

    @ApiModelProperty("角色对局数据")
    private List<CharacterStatsDto> characterData;

    @ApiModelProperty("场地数据")
    private List<StageStatsDto> stageData;

    public Integer getCount() {
        return win + lose;
    }

    public CharacterDto() {}

    public CharacterDto(Character character) {
        playerId = character.getPlayerId();
        this.character = character.getCharacter();
        win = character.getWin();
        lose = character.getLose();
        solo = character.getSolo();
        starter = character.getStarter();
        counter = character.getCounter();
        drop = character.getDrop();

        characterData = JSONObject.parseArray(character.getCharacterData(), CharacterStatsDto.class);
        stageData = JSONObject.parseArray(character.getStageData(), StageStatsDto.class);
    }

}
