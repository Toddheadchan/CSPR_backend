package com.cnsmash.cspr.backend.smashgg.graphql;

import lombok.extern.slf4j.Slf4j;
import org.mountcloud.graphql.GraphqlClient;
import org.mountcloud.graphql.request.query.GraphqlQuery;
import org.mountcloud.graphql.response.GraphqlResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GraphQLUtils {

    private static String url = "https://api.smash.gg/gql/alpha";
    private static String token = "Bearer 1837dae5a7ee7f0b0f2cc8f70024b280"; // Amaki用TOKEN1
    private static String token2 = "Bearer 6b8e821fa9783c5cb3049fde803f1d83"; // Amaki用TOKEN2
    private static String token3 = "Bearer f8a5a42328084d1bbf1772794d67288c"; // Amaki用TOKEN3

    public static int queryCounter = 0;
    public static LocalDateTime lastQuery;

    private static void limitCheck() {
        try {
            queryCounter += 1;
            LocalDateTime now = LocalDateTime.now();
            if (lastQuery == null) {
                return;
            }
            Duration duration = Duration.between(lastQuery, now);
            long second = duration.toMillis();
            if (second >= 1000) {
                return;
            } else {
                Thread.sleep(1100 - second);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    // 多个token交叉使用防止爆限制
    private static String getToken() {
        if (queryCounter % 3 == 0) return token;
        if (queryCounter % 3 == 1) return token3;
        return token2;
    }

    public static GraphqlResponse queryData(GraphqlQuery query) {
        limitCheck();
        GraphqlClient client = GraphqlClient.buildGraphqlClient(url);
        // 设置 http header
        Map httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization",getToken());
        httpHeaders.put("Content-Type","application/json");
        client.setHttpHeaders(httpHeaders);
        // 发起请求
        try {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info("query: " + query.toString());
            GraphqlResponse response = client.doQuery(query);
            log.info("response: " + response);
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            return response;
        } catch (Exception e) {
            log.error("!!!!!!!!!!!!!!!!!!ERROR!!!!!!!!!!!!!!!!!!");
            log.error(e.toString());
            // e.printStackTrace();
        }
        log.info("!!!RESTART QUERY!!!");
        return queryData(query);
    }

    public static GraphqlResponse queryData(GraphqlQueryExtend query) {
        limitCheck();
        GraphqlClient client = GraphqlClient.buildGraphqlClient(url);
        // 设置 http header
        Map httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization",getToken());
        httpHeaders.put("Content-Type","application/json");
        client.setHttpHeaders(httpHeaders);
        // 发起请求
        try {
            GraphqlResponse response = client.doQuery(query);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
