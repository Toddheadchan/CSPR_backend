package com.cnsmash.cspr.backend.script.character;

import com.cnsmash.cspr.api.dto.CharacterStatsDto;
import com.cnsmash.cspr.api.dto.StageStatsDto;
import com.cnsmash.cspr.api.entity.Character;
import com.cnsmash.cspr.api.entity.Game;
import com.cnsmash.cspr.api.entity.Set;
import com.cnsmash.cspr.api.service.ICharacterService;
import com.cnsmash.cspr.api.service.IGameService;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.utils.ApiSpringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class CharacterStatsManager {

    private Map<String, Character> characterDataMap;

    private Map<String, CharacterStatsDto> matchUpDataMap;

    private Map<String, StageStatsDto> stageDataMap;

    private Map<String, List<String>> characterMatchUpMap;

    private Map<String ,List<String>> characterStageMap;

    @Resource
    ISetService iSetService = (ISetService) ApiSpringUtil.getBean(ISetService.class);

    @Resource
    IGameService iGameService = (IGameService) ApiSpringUtil.getBean(IGameService.class);

    @Resource
    ICharacterService iCharacterService = (ICharacterService) ApiSpringUtil.getBean(ICharacterService.class);

    private String getCharacterDataId(long playerId, String character) {
        return playerId + "-" + character;
    }

    private String getMatchUpDataId(long playerId, String character, String oppoCharacter) {
        return playerId + "-" + character + "-" + oppoCharacter;
    }

    private String getStageDataId(long playerId, String character, String stage) {
        return playerId + "-" + character + "-" + stage;
    }

    private void saveData(long playerId, String stage, String myChara, String oppoChara, boolean winning, int lastGameStatus) {
        if (myChara == null) return;
        // character data
        String characterDataId = getCharacterDataId(playerId, myChara);
        if (this.characterDataMap.keySet().contains(characterDataId) == false) {
            this.characterDataMap.put(characterDataId, new Character());
            this.characterMatchUpMap.put(characterDataId, new LinkedList<>());
            this.characterStageMap.put(characterDataId, new LinkedList<>());
        }
        if (winning == true) {
            this.characterDataMap.get(characterDataId).addWin();
        } else if (winning == false) {
            this.characterDataMap.get(characterDataId).addLose();
        }

        // match up
        String matchUpDataId = getMatchUpDataId(playerId, myChara, oppoChara);
        if (this.matchUpDataMap.keySet().contains(matchUpDataId) == false) {
            CharacterStatsDto characterStatsDto = new CharacterStatsDto();
            characterStatsDto.setCharacter(oppoChara);
            this.matchUpDataMap.put(matchUpDataId, characterStatsDto);
            this.characterMatchUpMap.get(characterDataId).add(matchUpDataId);
        }
        if (winning == true) {
            this.matchUpDataMap.get(matchUpDataId).addWin();
        } else if (winning == false) {
            this.matchUpDataMap.get(matchUpDataId).addLose();
        }

        // stage
        if (stage != null) {
            String stageDataId = getStageDataId(playerId, myChara, stage);
            if (this.stageDataMap.keySet().contains(stageDataId) == false) {
                StageStatsDto stageStatsDto = new StageStatsDto();
                stageStatsDto.setStage(stage);
                this.stageDataMap.put(stageDataId, stageStatsDto);
                this.characterStageMap.get(characterDataId).add(stageDataId);
            }
            if (winning == true) {
                this.stageDataMap.get(stageDataId).addWin();
            } else if (winning == false) {
                this.stageDataMap.get(stageDataId).addLose();
            }

            // starter
            if (lastGameStatus == -1) {
                this.stageDataMap.get(stageDataId).addStarter();
            } else if (lastGameStatus == 0) {
                this.stageDataMap.get(stageDataId).addCounter();
            }
        }
    }

    private void characterStating(long playerId, List<String> playerCharacter) {
        if (playerCharacter.contains(null)) return;
        String lastCharacter = "";
        boolean characterChange = false;
        for (String character: playerCharacter) {
            if (character.equals(lastCharacter) == false) {
                String characterDataId = getCharacterDataId(playerId, character);
                if ("".equals(lastCharacter)) {
                    this.characterDataMap.get(characterDataId).addStarter();
                } else {
                    characterChange = true;
                    this.characterDataMap.get(characterDataId).addCounter();
                    this.characterDataMap.get(getCharacterDataId(playerId, lastCharacter)).addDrop();
                }
            }
            lastCharacter = character;
        }
        // 没换过角色则solo + 1
        if (characterChange == false) {
            this.characterDataMap.get(getCharacterDataId(playerId, lastCharacter)).addSolo();
        }
    }

    public void run() {

        // 初始化数据结构
        characterDataMap = new HashMap<>();
        matchUpDataMap = new HashMap<>();
        stageDataMap = new HashMap<>();
        characterMatchUpMap = new HashMap<>();
        characterStageMap = new HashMap<>();

        List<Set> sets = iSetService.list();

        for (Set set: sets) {
            List<String> player1Character = new LinkedList<>();
            List<String> player2Character = new LinkedList<>();
            List<Game> games = iGameService.getGameBySetId(set.getSetId());
            Long player1Id = set.getPlayer1Id();
            Long player2Id = set.getPlayer2Id();
            long lastWinner = -1;
            for (Game game: games) {
                this.saveData(player1Id, game.getStage(), game.getPlayer1Character(), game.getPlayer2Character(), game.getWinnerId().equals(player1Id), lastWinner == -1 ? -1 : (player1Id.equals(lastWinner) ? 1 : 0));
                this.saveData(player2Id, game.getStage(), game.getPlayer2Character(), game.getPlayer1Character(), game.getWinnerId().equals(player2Id), lastWinner == -1 ? -1 : (player2Id.equals(lastWinner) ? 1 : 0));
                player1Character.add(game.getPlayer1Character());
                player2Character.add(game.getPlayer2Character());
                lastWinner = set.getWinnerId();
            }
            if (games.size() != 0) {
                this.characterStating(player1Id, player1Character);
                this.characterStating(player2Id, player2Character);
            }
        }

        for (String playerCharacterId: characterDataMap.keySet()) {
            String[] idInfo = playerCharacterId.split("-");
            Character character = this.characterDataMap.get(playerCharacterId);
            character.setCharacterId(playerCharacterId);
            character.setPlayerId(Long.valueOf(idInfo[0]));
            character.setCharacter(idInfo[1]);
            boolean flag;
            String matchUpList = "["; flag = false;
            for (String id: this.characterMatchUpMap.get(playerCharacterId)) {
                if (flag == false) {
                    flag = true;
                } else {
                    matchUpList += ",";
                }
                matchUpList += matchUpDataMap.get(id).toJsonString();
            }
            matchUpList += "]";
            String stageList = "["; flag = false;
            for (String id: this.characterStageMap.get(playerCharacterId)) {
                if (flag == false) {
                    flag = true;
                } else {
                    stageList += ",";
                }
                stageList += stageDataMap.get(id).toJsonString();
            }
            stageList += "]";
            character.setCharacterData(matchUpList);
            character.setStageData(stageList);
            iCharacterService.saveOrUpdate(character);
        }

    }

}
