package com.cnsmash.cspr.backend.task;

import com.cnsmash.cspr.api.entity.Account;
import com.cnsmash.cspr.api.entity.League;
import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.service.IAccountService;
import com.cnsmash.cspr.api.service.ILeagueService;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import org.apache.tomcat.jni.Local;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TaskScheduler {

    @Resource
    static IAccountService iAccountService = (IAccountService) ApiSpringUtil.getBean(IAccountService.class);

    @Resource
    static ILeagueService iLeagueService = (ILeagueService) ApiSpringUtil.getBean(ILeagueService.class);

    @Resource
    static ITournamentService iTournamentService = (ITournamentService) ApiSpringUtil.getBean(ITournamentService.class);

    /**
     * 所有地区账号请求查看是否有新创建league的任务列表
     * @return 对应任务列表
     */
    private static List<Task> accountQueryTaskList() {
        List<Task> taskList = new LinkedList<>();
        List<Account> accountList = iAccountService.list();
        for (Account account: accountList) {
            taskList.add(new Task(TaskType.ACCOUNT_QUERY, account.getAccountId(), account.getRegionId()));
        }
        return taskList;
    }

    /**
     * 所有league列表中需要获取更新的league任务列表
     * @return 对应任务列表
     */
    private static List<Task> leagueQueryTaskList() {
        List<Task> taskList = new LinkedList<>();
        List<League> leagueList = iLeagueService.list();
        for (League league : leagueList) {
            // league已经结束
            if (league.getState() == 3) {
                continue;
            }
            // 计算上次更新时间
            LocalDateTime shouldUpdate = league.getLastUpdate().plusDays(2);
            LocalDateTime now = LocalDateTime.now();
            if (now.compareTo(shouldUpdate) == 1) {
                taskList.add(new Task(TaskType.CHECK_LEAGUE, league.getLeagueId(), league.getRegionId()));
            }
        }
        return taskList;
    }

    /**
     * 所有tournament列表中需要获取更新的tournament任务列表
     * @return 对应任务列表
     */
    private static List<Task> tournamentQueryTaskList() {
        List<Task> taskList = new LinkedList<>();
        List<Tournament> tournamentList = iTournamentService.list();
        for (Tournament tournament : tournamentList) {
            // tournament已经结束
            if (tournament.getStatus() == 3) {
                continue;
            }
            taskList.add(new Task(TaskType.CHECK_TOURNAMENT, tournament.getTournamentId()));
        }
        return taskList;
    }

    public static List<Task> getTaskSchedule() {
        List<Task> taskList = new LinkedList<>();
        taskList.addAll(accountQueryTaskList());
        taskList.addAll(leagueQueryTaskList());
        taskList.addAll(tournamentQueryTaskList());
        taskList.add(new Task(TaskType.CHARACTER_CALCULATION));
        taskList.add(new Task(TaskType.CSPR_CALCULATION, 1L * SeasonUtil.getCurrentSeason()));
        return taskList;
    }

}
