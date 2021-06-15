package com.cnsmash.cspr.backend.task;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class TaskManager {

    // 正在执行的任务，null=无正在执行的任务
    private Task running;

    // 积存任务列表
    private List<Task> taskList;


    public TaskManager () {
        this.taskList = new LinkedList<>();
        this.running = null;
    }

    /**
     * 执行队列中积压的任务
     * 每隔一定时间执行一次，若没有正在运行的任务则执行新任务
     */
    public void run() {
        if (this.running != null) {
            TaskStatus staus = this.running.getStatus();
            // 任务正在执行中，本次查询暂不执行
            if (staus == TaskStatus.RUNNING) {
                log.info("任务正在进行中");
                return;
            }
            if (staus == TaskStatus.FINISH) {
                this.running = null;
            }
        }
        // 执行队列第一个任务
        this.execute();
    }

    /**
     * 访问数据库查看是否有需要进行的任务
     * 添加至任务队列中
     */
    public void schedule() {
        // 若当前已无积压任务，则更新任务列表
        if (this.taskList.size() == 0) {
            this.taskList = TaskScheduler.getTaskSchedule();
        }
    }

    /**
     * 执行任务管理器
     */
    private void execute() {
        if (this.taskList.size() == 0) {
            log.info("当前任务队列为空");
            return;
        }
        this.running = taskList.get(0);
        log.info("执行： " + this.running.toString());
        this.running.execute();
        this.taskList.remove(0);
    }

}
