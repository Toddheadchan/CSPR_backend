package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.dto.CharacterDto;
import com.cnsmash.cspr.api.dto.CharacterStatsDto;
import com.cnsmash.cspr.framework.dto.KeyValueDto;
import com.cnsmash.cspr.api.dto.StageStatsDto;
import com.cnsmash.cspr.api.entity.Character;
import com.cnsmash.cspr.api.mapper.CharacterMapper;
import com.cnsmash.cspr.api.service.ICharacterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.vo.CharacterDetail;
import com.cnsmash.cspr.api.vo.CharacterMatchUp;
import com.cnsmash.cspr.api.vo.CharacterStage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Slf4j
@Service
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, Character> implements ICharacterService {

    @Autowired
    CharacterMapper characterMapper;

    @Override
    public CharacterDto getCharacterData(int playerId, String character) {
        Character characterInfo = characterMapper.getCharacterDataByPlayerAndCharacter(playerId, character);
        return new CharacterDto(characterInfo);
    }

    /**
     * 获取使用选手使用所有角色频率
     * @param playerId
     * @return
     */
    @Override
    public List<KeyValueDto<String, Double> > getPlayerCharacterRate(int playerId) {
        List<Character> characterList = characterMapper.getCharacterByPlayer(playerId);

        // 计算总共有数据的game数
        int totalCount = 0;
        for (Character character: characterList) {
            totalCount += character.getWin() + character.getLose();
        }

        List<KeyValueDto<String, Double> > result = new LinkedList<>();
        for (Character character: characterList) {
            result.add(new KeyValueDto<String, Double>(character.getCharacter(), 1.0 * (character.getWin() + character.getLose()) / totalCount * 100, "rate"));
        }
        Collections.sort(result, new Comparator<KeyValueDto<String, Double>>() {
            @Override
            public int compare(KeyValueDto<String, Double> o1, KeyValueDto<String, Double> o2) {
                if (o1.getValue() > o2.getValue()) return -1;
                else if (o1.getValue() == o2.getValue()) return 0;
                return 1;
            }
        });
        return result;
    }

    /**
     * 获取选手某一角色具体属于用于回显
     * @param playerId 玩家ID
     * @param character 角色名
     * @return 角色具体数据vo
     */
    @Override
    public CharacterDetail getCharacterDetail(int playerId, String character) {
        CharacterDto characterInfo = getCharacterData(playerId, character);
        List<CharacterStatsDto> characterStats = characterInfo.getCharacterData();
        List<StageStatsDto> stageStats = characterInfo.getStageData();

        CharacterDetail characterDetail = new CharacterDetail(characterInfo);

        // match up 统计
        CharacterStatsDto mostPlayed = new CharacterStatsDto();
        CharacterStatsDto mostWin = new CharacterStatsDto();
        CharacterStatsDto mostLose = new CharacterStatsDto();
        for (CharacterStatsDto characterStat: characterStats) {
            if (mostPlayed.totalCount() < characterStat.totalCount()) {
                mostPlayed = characterStat;
            }
            if (mostWin.winRate() < characterStat.winRate()) {
                mostWin = characterStat;
            }
            if (mostLose.loseRate() < characterStat.loseRate()) {
                mostLose = characterStat;
            }
        }
        CharacterMatchUp matchUp = new CharacterMatchUp();
        matchUp.setMostPlayed(new KeyValueDto<String, String>(mostPlayed.getCharacter(), mostPlayed.strValue(), "character"));
        matchUp.setMostWin(new KeyValueDto<String, String>(mostWin.getCharacter(), mostWin.strValue(), "character"));
        matchUp.setMostLose(new KeyValueDto<String, String>(mostLose.getCharacter(), mostLose.strValue(), "character"));

        // 地区统计
        StageStatsDto mostStarter = new StageStatsDto();
        StageStatsDto mostCounter = new StageStatsDto();
        StageStatsDto mostStageWin = new StageStatsDto();
        StageStatsDto mostStageLose = new StageStatsDto();
        for (StageStatsDto stageStat: stageStats) {
            if (mostStarter.getStarter() < stageStat.getStarter()) {
                mostStarter = stageStat;
            }
            if (mostCounter.getCounter() < stageStat.getCounter()) {
                mostCounter = stageStat;
            }
            if (mostStageWin.winRate() < stageStat.winRate()) {
                mostStageWin = stageStat;
            }
            if (mostStageLose.loseRate() < stageStat.loseRate()) {
                mostStageLose = stageStat;
            }
        }
        CharacterStage stage = new CharacterStage();
        stage.setStarter(new KeyValueDto<String, String>(mostStarter.getStage(), mostStarter.strValue(), "stage"));
        stage.setCounter(new KeyValueDto<String, String>(mostCounter.getStage(), mostCounter.strValue(), "stage"));
        stage.setMostWin(new KeyValueDto<String, String>(mostStageWin.getStage(), mostStageWin.strValue(), "stage"));
        stage.setMostLose(new KeyValueDto<String, String>(mostStageLose.getStage(), mostStageLose.strValue(), "stage"));

        characterDetail.setMatchUp(matchUp);
        characterDetail.setStage(stage);

        return characterDetail;
    }

}
