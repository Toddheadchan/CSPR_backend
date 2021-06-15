package com.cnsmash.cspr.backend.smashgg.script;

import com.cnsmash.cspr.api.entity.League;
import com.cnsmash.cspr.api.service.ILeagueService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.backend.smashgg.graphql.GraphQLScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class LeagueFetch {

    @Autowired
    static GraphQLScript graphQLScript;

    @Resource
    static ILeagueService iLeagueService = (ILeagueService) ApiSpringUtil.getBean(ILeagueService.class);

    // 获取最新的league列表
    public static void getLeagueByOwner(long ownerId, int regionId) {
        int page = 0;
        boolean stopFlag = false;
        List<Long> newLeagueList = new LinkedList<>();
        while (stopFlag == false) {
            page += 1;
            List<Long> leagueList = graphQLScript.getLeagueListByOwner(ownerId, page);
            for (Long leagueId: leagueList) {
                League queryLeague = iLeagueService.getById(leagueId);
                if (queryLeague == null) {
                    newLeagueList.add(leagueId);
                } else {
                    stopFlag = true;
                    break;
                }
            }
            if (leagueList.size() < 10) {
                stopFlag = true;
            }
        }
        for (Long leagueId: newLeagueList) {
            getLeagueDetail(leagueId, regionId);
        }
    }

    public static void updateLeague(long leagueId, int regionId) {
        League league = graphQLScript.getLeagueDetail(leagueId, regionId);
        League oldLeague = iLeagueService.getById(leagueId);
        iLeagueService.updateById(league);
        TournamentFetch.getTournamentByLeague(leagueId);
    }

    // 新的league获取详细信息后插入数据库
    public static void getLeagueDetail(long leagueId, int regionId) {
        League league = graphQLScript.getLeagueDetail(leagueId, regionId);
        iLeagueService.saveOrUpdate(league);
        TournamentFetch.getTournamentByLeague(leagueId);
    }

}
