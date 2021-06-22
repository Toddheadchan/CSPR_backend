package com.cnsmash.cspr.backend.task;

import com.cnsmash.cspr.backend.script.character.CharacterSumupManager;
import com.cnsmash.cspr.backend.smashgg.script.LeagueFetch;
import com.cnsmash.cspr.backend.smashgg.script.TournamentFetch;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task {

    // 任务类型
    private TaskType taskType;

    // 目标值
    private Long value;

    // 备用参数
    private Object subParam;

    // 任务状态
    private TaskStatus status;

    public TaskStatus getStatus() {
        return this.status;
    }

    // 无参构建函数
    public Task() {
        this.status = TaskStatus.PENDING;
    }

    // 单tasktype参数
    public Task(TaskType type) {
        this.taskType = type;
        this.status = TaskStatus.WAITING;
    }

    // 带参构建函数
    public Task(TaskType type, Long value) {
        this.taskType = type;
        this.value = value;
        this.status = TaskStatus.WAITING;
    }

    // 带备用参数构建函数
    public Task(TaskType type, Long value, Object subParam) {
        this.taskType = type;
        this.value = value;
        this.subParam = subParam;
        this.status = TaskStatus.WAITING;
    }

    // 任务执行函数
    public void execute() {
        this.status = TaskStatus.RUNNING;
        if (this.taskType == TaskType.ACCOUNT_QUERY) {
            LeagueFetch.getLeagueByOwner(this.value, (int)this.subParam);
        } else if (this.taskType == TaskType.CHECK_LEAGUE) {
            LeagueFetch.leagueFetchTask(this.value, (int)this.subParam);
        } else if (this.taskType == TaskType.CHECK_TOURNAMENT) {
            TournamentFetch.getTournamentDetail(this.value);
        } else if (this.taskType == TaskType.CSPR_CALCULATION) {
            // do cspr calculation
            log.info("cspr updated");
        } else if (this.taskType == TaskType.CHARACTER_CALCULATION) {
            CharacterSumupManager.run();
        }
        this.status = TaskStatus.FINISH;
    }

    public String toString() {
        String ret = "";
        // 查询账号
        if (this.taskType == TaskType.ACCOUNT_QUERY) {
            ret = "ACCOUNT QUERY, account = " + this.value;
        } else if (this.taskType == TaskType.CHECK_LEAGUE) {
            ret = "CHECK LEAGUE, league ID = " + this.value;
        } else if (this.taskType == TaskType.CHECK_TOURNAMENT) {
            ret = "CHECK TOURNAMENT, tournament ID = " + this.value;
        } else if (this.taskType == TaskType.CSPR_CALCULATION) {
            ret = "CSPR CALCULATION, season = " + this.value;
        }
        return ret;
    }

}
