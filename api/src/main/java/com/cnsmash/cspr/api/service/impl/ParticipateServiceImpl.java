package com.cnsmash.cspr.api.service.impl;

import com.cnsmash.cspr.api.entity.Participate;
import com.cnsmash.cspr.api.mapper.ParticipateMapper;
import com.cnsmash.cspr.api.service.IParticipateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.service.ISetService;
import com.cnsmash.cspr.api.vo.PlayerGame;
import com.cnsmash.cspr.api.vo.PlayerSet;
import com.cnsmash.cspr.api.vo.TournamentStanding;
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
public class ParticipateServiceImpl extends ServiceImpl<ParticipateMapper, Participate> implements IParticipateService {

    @Autowired
    ParticipateMapper participateMapper;

    @Autowired
    ISetService iSetService;

    public List<Participate> getPlayerInTournament(long tournamentId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("tournament_id", tournamentId);
        return participateMapper.selectByMap(queryMap);
    }

    public String countTournamentCharacter(long tournamentId, long playerId) {
        Map<String, Integer> characterMap = new HashMap();
        List<PlayerSet> sets = iSetService.getPlayerSetDetail(playerId, tournamentId);
        for (PlayerSet set: sets) {
            List<PlayerGame> games = set.getGames();
            for (PlayerGame game: games) {
                String character = game.getMyCharacter();
                if (characterMap.containsKey(character) == false) {
                    characterMap.put(character, 1);
                } else {
                    Integer count = characterMap.get(character) + 1;
                    characterMap.put(character, count);
                }
            }
        }
        List<String> characterList = new LinkedList<>();
        for (String character: characterMap.keySet()) {
            characterList.add(character);
        }
        Collections.sort(characterList, new Comparator<String>() {
            @Override
            public int compare(String character1, String character2) {
                int c1 = characterMap.get(character1);
                int c2 = characterMap.get(character2);
                if (c1 < c2) return 1;
                else if (c1 == c2) return 0;
                return -1;
            }
        });
        boolean flag = false;
        String ret = "";
        for (String character: characterList) {
            if (flag == false) {
                flag = true;
            } else {
                ret += ",";
            }
            ret += character;
        }
        return ret;
    }

    public void updatePlayerId(long subPlayerId, long mainPlayerId) {
        participateMapper.updatePlayerId(subPlayerId, mainPlayerId);
    }

}
