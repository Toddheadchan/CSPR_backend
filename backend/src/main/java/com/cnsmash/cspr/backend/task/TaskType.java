package com.cnsmash.cspr.backend.task;

public enum TaskType {

    // 根据账号查询league列表
    ACCOUNT_QUERY,

    // 根据ID更新league内容
    CHECK_LEAGUE,

    // 根据ID更新tournament内容
    CHECK_TOURNAMENT,

    // 重新计算CSPR
    CSPR_CALCULATION,

    // 计算角色数据
    CHARACTER_CALCULATION

}
