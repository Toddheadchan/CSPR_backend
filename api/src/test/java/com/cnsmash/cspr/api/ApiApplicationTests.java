package com.cnsmash.cspr.api;

import com.cnsmash.cspr.api.controller.PlayerController;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.cnsmash.cspr.api.service.IPlayerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ApiApplicationTests {

    @Autowired
    IParticipateService iParticipateService;

    @Autowired
    IPlayerService iPlayerService;

    @Test
    void contextLoads() {
    }

    @Test
    void getPlayerCharacter() {
        log.info(iParticipateService.countTournamentCharacter(524786, 1806993));
    }

    @Test
    void combinePlayer() {
        iPlayerService.attachPlayerId(2054273,1713595);
    }

}
