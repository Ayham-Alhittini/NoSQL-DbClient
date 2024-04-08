package com.decentraldbcluster.dbclient;

import com.decentraldbcluster.dbclient.queries.CollectionQuery;
import com.decentraldbcluster.dbclient.queries.DocumentQuery;
import com.decentraldbcluster.dbclient.queries.IndexQuery;
import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.decentraldbcluster.dbclient.validation.QueryValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class DbClient {

    private final DbConnection connection;

    public DbClient(DbConnection connection) {
        this.connection = connection;
    }

    public QueryResponse executeQuery(Query query) throws IllegalArgumentException {
        try {
            QueryValidator.validate(query);

            configureQueryFromAPICredentials(query);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(query);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( getTargetUrlRequest(query) ))
                    .header("Authorization", connection.getToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new QueryResponse(response.statusCode(), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
