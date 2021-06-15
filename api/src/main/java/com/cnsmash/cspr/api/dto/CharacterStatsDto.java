package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("角色数据结构")
@Data
public class CharacterStatsDto implements Serializable {

    @ApiModelProperty("（对方）角色名称")
    public String character;

    @ApiModelProperty("胜利次数")
    public Integer win;

    @ApiModelProperty("败北次数")
    public Integer lose;

    public int totalCount() {
        return win + lose;
    }

    public String strValue() {
        return String.format("%d-%d (%.2f%%)", win, lose, 100.0 * win / totalCount());
    }

    public CharacterStatsDto() {
        this.character = "null";
        this.win = 0;
        this.lose = 0;
    }

    public void addWin() {
        this.win += 1;
    }

    public void addLose() {
        this.lose += 1;
    }

    public String toJsonString() {
        String res = "{";
        res += "\"character\": \"" + this.character + "\",";
        res += "\"win\": \"" + this.win + "\",";
        res += "\"lose\": \"" + this.lose + "\"";
        res += "}";
        return res;
    }

    public double winRate() {
        if (this.totalCount() == 0) {
            return 0;
        }
        return 1.0 * this.win / this.totalCount();
    }

    public double loseRate() {
        if (this.totalCount() == 0) {
            return 0;
        }
        return 1.0 * this.lose / this.totalCount();
    }
}
