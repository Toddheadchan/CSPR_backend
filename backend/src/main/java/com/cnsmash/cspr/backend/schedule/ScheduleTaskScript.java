package com.cnsmash.cspr.backend.schedule;

import com.cnsmash.cspr.backend.task.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ScheduleTaskScript {

    private static int count = 0;

    private static TaskManager taskManager = new TaskManager();

    private static boolean flag = false;

    @Scheduled(cron = "0 0 2/12 * * *")
    public void backendTask() {
        taskManager.schedule();
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void taskExecute() {
        if (flag == false) {
            flag = true;
            this.backendTask();
        }
        taskManager.run();
    }

}
