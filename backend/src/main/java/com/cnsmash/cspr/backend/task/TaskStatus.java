package com.cnsmash.cspr.backend.task;

public enum TaskStatus {

    // 等待执行
    WAITING,

    // 正在运行
    RUNNING,

    // 等待数据
    PENDING,

    // 执行完毕
    FINISH,

    // 被中断
    ABORTED

}
