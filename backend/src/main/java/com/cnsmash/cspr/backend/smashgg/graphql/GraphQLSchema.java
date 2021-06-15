package com.cnsmash.cspr.backend.smashgg.graphql;

import lombok.extern.slf4j.Slf4j;
import org.mountcloud.graphql.request.query.GraphqlQuery;

@Slf4j
public class GraphQLSchema {

    private static String stringQueryFormatter(String query) {
        return "{\"query\":\"" + query + "\"}";
    }

    /************************* GET THE LIST OF EACH DATA STRUCTURE *************************/

    public static GraphqlQuery queryLeagueByOwner(long ownerId, int page) {
        String query = "{" +
                "  tournaments(query: {" +
                "    page: " + String.valueOf(page) + "," +
                "    perPage: 10," +
                "    filter: {" +
                "      ownerId: " + String.valueOf(ownerId) +
                "    }" +
                "  }) {" +
                "    nodes {" +
                "      id" +
                "    }" +
                "  }" +
                "}";

        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("LeagueQuery");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery queryTournamentListByLeague(long leagueId) {
        String query = "{" +
                "  tournament(id: " + String.valueOf(leagueId) + ") {" +
                "    events {" +
                "      id" +
                "      type" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("TournamentQuery");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery querySetListByTournament(long tournamentId, int page) {
        String query = "{" +
                "  event(id: " + String.valueOf(tournamentId) + ") {" +
                "    sets(page: " + String.valueOf(page) + ", perPage: 30) {" +
                "      nodes {" +
                "        id" +
                "      }" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("SetsQuery");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    /************************* GET THE DETAIL OF EACH DATA STRUCTURE *************************/

    public static GraphqlQuery queryLeagueDetail(long leagueId) {
        String query = "{ " +
                "  tournament(id: " + leagueId + ") {" +
                "    name" +
                "    state" +
                "    slug" +
                "    owner {" +
                "      id" +
                "    }" +
                "    images {" +
                "      type" +
                "      url" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("LeagueDetail");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery queryTournamentStanding(long tournamentId, int page) {
        String query = "{ " +
                "  event(id: " + String.valueOf(tournamentId) + ") {" +
                "    standings(query: {" +
                "      page: " + String.valueOf(page) +
                "      perPage: 30" +
                "    }) {" +
                "      nodes {" +
                "        placement" +
                "        entrant {" +
                "          isDisqualified" +
                "          participants {" +
                "            player {" +
                "              id" +
                "              gamerTag" +
                "            }" +
                "          }" +
                "        }" +
                "      }" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("TournamentStanding");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery queryTournamentDetail(long tournamentId) {
        String query = "query tournamentDetail {" +
                "  event(id:" + String.valueOf(tournamentId) + " ){" +
                "    id" +
                "    slug" +
                "    name" +
                "    tournament {" +
                "      id" +
                "    }" +
                "    startAt" +
                "    numEntrants" +
                "    state" +
                "    isOnline" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("TournamentDetail");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery querySetDetail(long setId) {
        String query = "query setDetail {" +
                "  set(id: " + String.valueOf(setId) + ") {" +
                "    id" +
                "    displayScore" +
                "    round" +
                "    fullRoundText" +
                "    state" +
                "    winnerId" +
                "    event {" +
                "      id" +
                "    }" +
                "    slots {" +
                "      entrant {" +
                "        id" +
                "        participants {" +
                "          player {" +
                "            id" +
                "            gamerTag" +
                "          }" +
                "        }" +
                "      }" +
                "      standing {" +
                "        stats {" +
                "          score {" +
                "            label" +
                "            value" +
                "            displayValue" +
                "          }" +
                "        }" +
                "      }" +
                "    }" +
                "    games {" +
                "      id" +
                "      orderNum" +
                "      stage {" +
                "        name" +
                "      }" +
                "      selections {" +
                "        entrant {" +
                "          id" +
                "        }" +
                "        selectionType" +
                "        selectionValue" +
                "      }" +
                "      winnerId" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("SetDetail");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

    public static GraphqlQuery queryPlayerDetail(long playerId) {
        String query = "query playerQuery{" +
                "  player(id: "+ playerId +" ) {" +
                "    gamerTag" +
                "    prefix" +
                "    user {" +
                "      images {" +
                "        url" +
                "        type" +
                "      }" +
                "    }" +
                "  }" +
                "}";
        GraphqlQueryExtend graphqlQuery = new GraphqlQueryExtend("TournamentDetail");
        graphqlQuery.setStringValue(stringQueryFormatter(query));
        return graphqlQuery;
    }

}
