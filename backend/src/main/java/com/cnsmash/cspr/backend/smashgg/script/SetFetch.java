package com.cnsmash.cspr.backend.smashgg.script;

import com.cnsmash.cspr.api.entity.Game;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.service.IGameService;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.service.IStatsService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.backend.smashgg.graphql.GraphQLScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SetFetch {

    @Autowired
    static GraphQLScript graphQLScript;

    @Autowired
    static ISetService iSetService = (ISetService) ApiSpringUtil.getBean(ISetService.class);

    @Autowired
    static IGameService iGameService = (IGameService) ApiSpringUtil.getBean(IGameService.class);

    @Autowired
    static IStatsService iStatsService = (IStatsService) ApiSpringUtil.getBean(IStatsService.class);

    public static void getSetByTournament(long tournamentId) {
        int page = 0;
        boolean stopFlag = false;
        List<Long> setList = new LinkedList<>();
        while (stopFlag == false) {
            page += 1;
            List<Long> tmpList = graphQLScript.getSetListByTournament(tournamentId, page);
            setList.addAll(tmpList);
            if (tmpList.size() < 10) {
                stopFlag = true;
            }
        }
        for (Long setId: setList) {
            getSetDetail(setId);
            /*
            if (iSetService.queryById(setId) != null) {
                updateSet(setId);
            } else {
                getSetDetail(setId);
            }
            */
        }
    }

    public static void updateSet(long setId) {
        Map<Object, Object> result = graphQLScript.getSetDetail(setId);
        Set set = (Set)result.get("set");
        iSetService.updateById(set);
        if (set.getDq() != 1) {
            iStatsService.loadSetStats(set);
        }
        saveGameData((List)result.get("games"));
    }

    public static void getSetDetail(long setId) {
        Map<Object, Object> result = graphQLScript.getSetDetail(setId);
        Set set = (Set)result.get("set");
        iSetService.saveOrUpdate(set);
        if (set.getDq() != 1) {
            iStatsService.loadSetStats(set);
        }
        saveGameData((List)result.get("games"));
    }

    public static void saveGameData(List<Game> gameList) {
        for (Game game: gameList) {
            iGameService.saveOrUpdate(game);
            /*
            if (iGameService.getById(game.getGameId()) == null) {
                iGameService.save(game);
            } else {
                iGameService.updateById(game);
            }
            */
        }
    }
}
