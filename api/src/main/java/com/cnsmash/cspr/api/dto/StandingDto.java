package com.cnsmash.cspr.api.dto;

import lombok.Data;

/**
 * 排名数据结构体
 */
@Data
public class StandingDto {

    // 玩家ID
    private long playerId;

    // 玩家TAG
    private String playerTag;

    // 排名
    private int standing;

    // 使用角色
    private String character;

    // 是否DQ
    private Boolean isDisqualified;

}
