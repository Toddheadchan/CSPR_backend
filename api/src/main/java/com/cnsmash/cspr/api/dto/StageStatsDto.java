package com.cnsmash.cspr.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("地图数据结构")
@Data
public class StageStatsDto implements Serializable {

    @ApiModelProperty("地图名称")
    private String stage;

    @ApiModelProperty("胜利次数")
    private Integer win;

    @ApiModelProperty("败北次数")
    private Integer lose;

    @ApiModelProperty("starter次数")
    private Integer starter;

    @ApiModelProperty("counter次数")
    private Integer counter;

    public Integer totalCount() {
        return win + lose;
    }

    public String strValue() {
        return String.format("%d-%d (%.2f%%)", win, lose, 100.0 * win / totalCount());
    }

    public StageStatsDto() {
        this.win = 0;
        this.lose = 0;
        this.starter = 0;
        this.counter = 0;
    }

    public void addWin() {
        this.win += 1;
    }

    public void addLose() {
        this.lose += 1;
    }

    public void addStarter() {
        this.starter += 1;
    }

    public void addCounter() {
        this.counter += 1;
    }

    public String toJsonString() {
        String res = "{";
        res += "\"stage\": \"" + this.stage + "\",";
        res += "\"win\": \"" + this.win + "\",";
        res += "\"lose\": \"" + this.lose + "\",";
        res += "\"starter\": \"" + this.starter + "\",";
        res += "\"counter\": \"" + this.counter + "\"";
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
