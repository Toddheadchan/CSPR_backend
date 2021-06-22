package com.cnsmash.cspr.backend;

import com.cnsmash.cspr.api.entity.Tournament;
import com.cnsmash.cspr.api.service.ITournamentService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.backend.script.character.CharacterStatsManager;
import com.cnsmash.cspr.backend.script.character.CharacterSumupManager;
import com.cnsmash.cspr.backend.script.cspr.CsprManager;
import com.cnsmash.cspr.backend.smashgg.script.LeagueFetch;
import com.cnsmash.cspr.backend.smashgg.script.TournamentFetch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class BackendApplicationTests {


    @Test
    void csprTest() {
        CsprManager csprManager = new CsprManager();
        csprManager.calculate(0);
        csprManager.calculate(1);
    }

    @Resource
    ITournamentService iTournamentService;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllTournament() {
        LeagueFetch.getLeagueByOwnerThroughAll(689403, 5);
    }

    @Test
    void character() {
        CharacterStatsManager characterStatsManager = new CharacterStatsManager();
        characterStatsManager.run();
    }

    @Test
    void tournamentCharacter() {
        TournamentFetch.updateTournamentCharacter(524786);
    }

    @Test
    void refetchTournament() {
        List<Tournament> tournamentList = iTournamentService.list();
        for (Tournament tournament: tournamentList) {
            log.info("updating tournament: " + tournament.getName());
            TournamentFetch.getTournamentDetail(tournament.getTournamentId());
        }
    }

    @Test
    void characterSumup() {
        CharacterSumupManager characterSumupManager = new CharacterSumupManager();
        characterSumupManager.run();
    }

    @Test
    void characterCountTest() {
        CharacterSumupManager characterSumupManager = new CharacterSumupManager();
        characterSumupManager.getMostCharacter(1713595);
    }

}
