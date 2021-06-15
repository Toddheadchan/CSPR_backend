package com.cnsmash.cspr.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "`character`")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "character_id")
    private String characterId;

    private Long playerId;

    @TableField(value = "`character`")
    private String character;

    @TableField(value = "`win`")
    private Integer win;

    @TableField(value = "`lose`")
    private Integer lose;

    @TableField(value = "`solo`")
    private Integer solo;

    @TableField(value = "`starter`")
    private Integer starter;

    @TableField(value = "`counter`")
    private Integer counter;

    @TableField(value = "`drop`")
    private Integer drop;

    private String characterData;

    private String stageData;


    public Character() {
        this.win = 0;
        this.lose = 0;
        this.solo = 0;
        this.starter = 0;
        this.counter = 0;
        this.drop = 0;
    }

    /********** 简易数据更新方法 **********/
    public int totalGame() {
        return this.win + this.lose;
    }

    public void addWin() {
        this.win += 1;
    }

    public void addLose() {
        this.lose += 1;
    }

    public void addSolo() {
        this.solo += 1;
    }

    public void addStarter() { this.starter += 1; }

    public void addCounter() {
        this.counter += 1;
    }

    public void addDrop() {
        this.drop += 1;
    }

}
