package com.cnsmash.cspr.backend.smashgg.graphql;

import com.cnsmash.cspr.api.dto.StandingDto;
import com.cnsmash.cspr.api.entity.*;
import com.cnsmash.cspr.framework.utils.SeasonUtil;
import com.cnsmash.cspr.framework.properties.CharacterProperties;
import lombok.extern.slf4j.Slf4j;
import org.mountcloud.graphql.request.query.GraphqlQuery;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GraphQLScript {

    public static List<Long> getLeagueListByOwner(long ownerId, int page) {
        log.info("query league list, ownerId=" + ownerId + ", page=" + page);
        GraphqlQuery query = GraphQLSchema.queryLeagueByOwner(ownerId, page);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        List<Map<String, Object>> nodeList = (List<Map<String, Object>>) ((Map)((Map)result.get("data")).get("tournaments")).get("nodes");
        List<Long> leagueList = new LinkedList<>();
        for (Map<String, Object> node: nodeList) {
            leagueList.add(Long.valueOf(node.get("id").toString()));
        }
        return leagueList;
    }

    public static List<Long> getTournamentListByLeague(long leagueId) {
        log.info("query tournament list, leagueId=" + leagueId);
        GraphqlQuery query = GraphQLSchema.queryTournamentListByLeague(leagueId);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        List<Map<String, Object>> nodeList = (List<Map<String, Object>>) ((Map)((Map)result.get("data")).get("tournament")).get("events");
        List<Long> tournamentList = new LinkedList<>();
        for (Map<String, Object> node: nodeList) {
            Integer type = (Integer) node.get("type");
            String str = node.get("id").toString();
            if (type == 1) {
                tournamentList.add(Long.valueOf(str));
            }
        }
        return tournamentList;
    }

    public static List<Long> getSetListByTournament(long tournamentId, int page) {
        log.info("query set list, tournamentId=" + tournamentId + ", page=" + page);
        GraphqlQuery query = GraphQLSchema.querySetListByTournament(tournamentId, page);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        List<Map<String, Object>> nodeList = (List<Map<String, Object>>) ((Map)((Map)((Map)result.get("data")).get("event")).get("sets")).get("nodes");
        List<Long> setList = new LinkedList<>();
        for (Map<String, Object> node: nodeList) {
            // 未进行的比赛无法插入，筛去
            if (node.get("id").toString().contains("preview")) {
                continue;
            }
            setList.add(Long.valueOf(node.get("id").toString()));
        }
        return setList;
    }

    public static League getLeagueDetail(long leagueId, int regionId) {
        log.info("query league detail, leagueId=" + leagueId);
        GraphqlQuery query = GraphQLSchema.queryLeagueDetail(leagueId);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        Map<String, Object> leagueMap = (Map)((Map)result.get("data")).get("tournament");
        League league = new League();
        league.setLeagueId(leagueId);
        league.setOwnerId(Long.valueOf(((Map)leagueMap.get("owner")).get("id").toString()));
        league.setName(leagueMap.get("name").toString());
        league.setState(Integer.valueOf(leagueMap.get("state").toString()));
        league.setSlug(leagueMap.get("slug").toString());
        league.setLastUpdate(LocalDateTime.now());
        league.setRegionId(regionId);
        List<Map<Object, Object>> imageList = (List)leagueMap.get("images");
        for (Map<Object, Object> image: imageList) {
            if ("profile".equals(image.get("type").toString())) {
                league.setLogo(image.get("url").toString());
            }
        }
        return league;
    }

    public static List<StandingDto> getTournamentStanding(long tournamentId, int page) {
        log.info("query tournament standing, tournamentId=" + tournamentId + ", page=" + page);
        GraphqlQuery query = GraphQLSchema.queryTournamentStanding(tournamentId, page);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        List<Map<Object, Object>> nodeList = (List<Map<Object, Object>>) ((Map)((Map)((Map)result.get("data")).get("event")).get("standings")).get("nodes");
        List<StandingDto> standingList = new LinkedList<>();
        for (Map<Object, Object> node: nodeList) {
            StandingDto standing = new StandingDto();
            standing.setStanding((int)node.get("placement"));
            List<Map<Object, Object>> participants = (List<Map<Object, Object>>)((Map)node.get("entrant")).get("participants");
            Boolean isDisqualified = (Boolean)((Map)node.get("entrant")).get("isDisqualified");
            if (isDisqualified == null) {
                standing.setIsDisqualified(false);
            } else {
                standing.setIsDisqualified(true);
            }
            for (Map<Object, Object> participant: participants) {
                Map<Object, Object> player = (Map)participant.get("player");
                standing.setPlayerId((int)player.get("id"));
                standing.setPlayerTag(player.get("gamerTag").toString());
            }
            standingList.add(standing);
        }
        return standingList;
    }

    public static Tournament getTournamentDetail(long tournamentId) {
        log.info("query tournament detail, tournamentId=" + tournamentId);
        GraphqlQuery query = GraphQLSchema.queryTournamentDetail(tournamentId);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        Map<Object, Object> event = (Map) ((Map)result.get("data")).get("event");
        Tournament tournament = new Tournament();
        tournament.setTournamentId(tournamentId);
        tournament.setLeagueId(((Integer)((Map)event.get("tournament")).get("id")).longValue());
        tournament.setSlug(event.get("slug").toString());
        tournament.setType(1);
        tournament.setName(event.get("name").toString());
        LocalDateTime time =LocalDateTime.ofEpochSecond(((Integer)event.get("startAt")),0,ZoneOffset.ofHours(8));
        tournament.setDate(time);
        tournament.setSeason(SeasonUtil.getSeasonByDate(time));
        String status = event.get("state").toString();
        if ("COMPLETED".equals(status)) {
            tournament.setStatus(2);
        } else if ("CREATED".equals(status)) {
            tournament.setStatus(0);
        } else {
            tournament.setStatus(-1);
        }
        tournament.setEntrants((Integer)event.get("numEntrants"));
        tournament.setCsprRate(0);
        tournament.setCsprRank(1);
        tournament.setOnline((boolean)event.get("isOnline") ? 1 : 0); ////
        return tournament;
    }

    public static Map<Object, Object> getSetDetail(long setId) {
        log.info("query set detail, setId=" + setId);
        GraphqlQuery query = GraphQLSchema.querySetDetail(setId);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        Map<Object, Object> setMap = (Map) ((Map)result.get("data")).get("set");
        // 组装set
        Set set = new Set();
        set.setSetId(setId);
        set.setTournamentId(((Integer)((Map)setMap.get("event")).get("id")).longValue());
        set.setRoundNumber((Integer)setMap.get("round"));
        set.setRoundText(setMap.get("fullRoundText").toString());
        String displayScore = setMap.get("displayScore").toString();
        set.setDisplayScore(displayScore);
        if ("DQ".equals(displayScore)) {
            set.setDq(1);
        } else {
            set.setDq(0);
        }
        List<Map<Object, Object> > slots = (List)setMap.get("slots");
        int playerIndex = 1;
        long player1EntrantId = 0;
        Map<Integer, Integer> idMap = new HashMap<>();
        for (Map<Object, Object> slot: slots) {
            Map<Object, Object> entrant = (Map) slot.get("entrant");
            Integer entrantId = (Integer) entrant.get("id");
            if (playerIndex == 1) {
                player1EntrantId = entrantId;
            }
            Map<Object, Object> player = (Map) ((Map)((List)entrant.get("participants")).get(0)).get("player");
            if (playerIndex == 1) {
                set.setPlayer1Id(Long.valueOf(player.get("id").toString()));
            } else {
                set.setPlayer2Id(Long.valueOf(player.get("id").toString()));
            }
            idMap.put(entrantId, (Integer) player.get("id"));

            Map<Object, Object> score = (Map)((Map)((Map)slot.get("standing")).get("stats")).get("score");
            Integer scoreValue = (Integer) score.get("value");
            if (scoreValue == null) scoreValue = new Integer(-1);
            if (playerIndex == 1) {
                set.setPlayer1Score(scoreValue);
            } else {
                set.setPlayer2Score(scoreValue);
            }

            playerIndex += 1;
        }

        set.setWinnerId(Long.valueOf(idMap.get(setMap.get("winnerId")).toString()));

        List<Game> gameList = new LinkedList<>();
        Map<String, String> characterIdMap = CharacterProperties.getCharacterIdMap();
        if (setMap.get("games") != null) {
            List<Map<Object, Object>> games = (List) setMap.get("games");
            for (Map<Object, Object> gameMap : games) {
                Game game = new Game();

                game.setGameId(Long.valueOf(gameMap.get("id").toString()));
                game.setSetId(setId);
                game.setGameIndex((Integer) gameMap.get("orderNum"));
                game.setPlayer1Stock(-1);
                game.setPlayer2Stock(-1);

                List<Map<Object, Object>> selections = (List) gameMap.get("selections");
                if (selections != null) {
                    for (Map<Object, Object> selection: selections) {
                        long entrantId = Long.valueOf(((Map)selection.get("entrant")).get("id").toString());
                        if (entrantId == player1EntrantId) {
                            game.setPlayer1Character(characterIdMap.get(selection.get("selectionValue").toString()));
                        } else {
                            game.setPlayer2Character(characterIdMap.get(selection.get("selectionValue").toString()));
                        }
                    }
                }

                Map<Object, Object> stage = (Map) gameMap.get("stage");
                if (stage != null) {
                    game.setStage(stage.get("name").toString());
                }

                game.setWinnerId(Long.valueOf(idMap.get(gameMap.get("winnerId")).toString()));
                gameList.add(game);
            }
        }

        Map<Object, Object> ret = new HashMap<>();
        ret.put("set", set);
        ret.put("games", gameList);
        return ret;
    }

    public static Player getPlayerDetail(Long playerId) {
        log.info("query player detail, playerId=" + playerId);
        GraphqlQuery query = GraphQLSchema.queryPlayerDetail(playerId);
        Map<Object, Object> result = GraphQLUtils.queryData(query).getData();
        Map<Object, Object> playerMap = (Map)((Map)result.get("data")).get("player");
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setPlayerTag(playerMap.get("gamerTag").toString());
        Map<Object, Object> user = (Map)playerMap.get("user");
        if (user != null) {
            List<Map<Object, Object>> images = (List) user.get("images");
            for (Map<Object, Object> imageMap : images) {
                if ("profile".equals(imageMap.get("type"))) {
                    player.setAvatar(imageMap.get("url").toString());
                }
            }
        }
        return player;
    }

}
