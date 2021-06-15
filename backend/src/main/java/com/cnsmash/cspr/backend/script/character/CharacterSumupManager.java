package com.cnsmash.cspr.backend.script.character;

import com.cnsmash.cspr.api.entity.Player;
import com.cnsmash.cspr.api.service.IPlayerService;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import com.cnsmash.cspr.api.vo.PlayerGame;
import com.cnsmash.cspr.api.vo.PlayerSet;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CharacterSumupManager {

    @Resource
    private static ISetService iSetService = (ISetService) ApiSpringUtil.getBean(ISetService.class);

    @Resource
    private static IPlayerService iPlayerService = (IPlayerService) ApiSpringUtil.getBean(IPlayerService.class);

    public static String getMostCharacter(long playerId) {
        List<PlayerSet> playerSets = iSetService.getSetsByPlayer(playerId);
        Map<String, Integer> characterCount = new HashMap<>();
        for (PlayerSet playerSet: playerSets) {
            if (playerSet.getGames() == null) continue;
            for (PlayerGame playerGame: playerSet.getGames()) {
                String character = playerGame.getMyCharacter();
                if (characterCount.keySet().contains(character) == false) {
                    characterCount.put(character, 1);
                } else {
                    int tmp = characterCount.get(character);
                    characterCount.put(character, tmp + 1);
                }
            }
        }
        int maxCount = 0;
        String ret = "";
        for (String character: characterCount.keySet()) {
            int tmp = characterCount.get(character);
            if (maxCount <= tmp) {
                maxCount = tmp;
                ret = character;
            }
        }
        return ret;
    }

    public static void run() {
        List<Player> playerList = iPlayerService.list();
        for (Player player: playerList) {
            String mostCharacter = getMostCharacter(player.getPlayerId());
            if ("".equals(mostCharacter)) {
                continue;
            }
            player.setMostCharacter(mostCharacter);
            iPlayerService.updateById(player);
        }
    }

}
