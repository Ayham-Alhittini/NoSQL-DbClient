package com.decentraldbcluster.dbclient.core;

import com.decentraldbcluster.dbclient.config.ConfigLoader;
import com.decentraldbcluster.dbclient.config.Configuration;
import com.decentraldbcluster.dbclient.odm.statetracker.DatabaseStateTracker;
import com.decentraldbcluster.dbclient.query.types.CollectionQuery;
import com.decentraldbcluster.dbclient.query.types.DocumentQuery;
import com.decentraldbcluster.dbclient.query.types.IndexQuery;
import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.decentraldbcluster.dbclient.validation.QueryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class DbClient {

    private static DbClient instance;
    private final ObjectMapper mapper = new ObjectMapper();
    private final DbConnection connection;

    private DbClient() {
        ConfigLoader.loadConfigurationFromProperties();
        connection = new DbConnection(Configuration.getApiKey(), Configuration.getToken());
    }

    public static DbClient getInstance() {
        if (instance == null) {
            instance = new DbClient();
            DatabaseStateTracker.track();
        }
        return instance;
    }

    public QueryResponse executeQuery(Query query) throws IllegalArgumentException {
        try {
            QueryValidator.validate(query);
            configureQueryFromAPICredentials(query);
            JsonNode jsonQuery = mapper.valueToTree(query);
            return getQueryResponse(query, jsonQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private QueryResponse getQueryResponse(Query query, JsonNode jsonQuery) throws IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse(query, jsonQuery);
        return new QueryResponse(response.statusCode(), response.body());
    }

    private HttpResponse<String> getStringHttpResponse(Query query, JsonNode jsonQuery) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = getHttpRequest(query, jsonQuery);
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest getHttpRequest(Query query, JsonNode jsonQuery) {
        return HttpRequest.newBuilder()
                .uri(URI.create( getTargetUrlRequest(query) ))
                .header("Authorization", connection.getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonQuery.toString(), StandardCharsets.UTF_8))
                .build();
    }

    private void configureQueryFromAPICredentials(Query query) {
        query.setOriginator(connection.getOriginator());
        query.setDatabase(connection.getDatabase());
    }

    private String getTargetUrlRequest(Query query) {
        return connection.getNodeAddress() + "/api/query/" + getEndpoint(query);
    }

    private String getEndpoint(Query query) {
        if (query instanceof CollectionQuery)
            return "collectionQueries";
        else if (query instanceof DocumentQuery)
            return "documentQueries";
        else if (query instanceof IndexQuery)
            return "indexQueries";
        throw new UnsupportedOperationException("Unexpected query type");
    }
}
