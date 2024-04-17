package com.decentraldbcluster.dbclient.core.query;

import com.decentraldbcluster.dbclient.core.db.DbConnection;
import com.decentraldbcluster.dbclient.core.http.HttpRequestFactory;
import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.decentraldbcluster.dbclient.validation.QueryValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QueryExecutor {
    private final ObjectMapper mapper = new ObjectMapper();
    private final DbConnection connection;
    private final HttpClient httpClient;

    public QueryExecutor(DbConnection connectionManager) {
        this.connection = connectionManager;
        this.httpClient = HttpClient.newHttpClient();
    }

    public QueryResponse executeQuery(Query query) {
        QueryValidator.validate(query);
        QueryConfigurator.configure(query, connection);
        JsonNode jsonQuery = mapper.valueToTree(query);
        HttpRequest request = HttpRequestFactory.createRequest(query, jsonQuery, connection);
        return execute(request);
    }

    private QueryResponse execute(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new QueryResponse(response.statusCode(), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
