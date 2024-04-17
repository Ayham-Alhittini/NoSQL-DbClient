package com.decentraldbcluster.dbclient.core.http;

import com.decentraldbcluster.dbclient.core.db.DbConnection;
import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.types.CollectionQuery;
import com.decentraldbcluster.dbclient.query.types.DocumentQuery;
import com.decentraldbcluster.dbclient.query.types.IndexQuery;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class HttpRequestFactory {
    public static HttpRequest createRequest(Query query, JsonNode jsonQuery, DbConnection connection) {
        return HttpRequest.newBuilder()
                .uri(URI.create( getTargetUrlRequest(query, connection) ))
                .header("Authorization", connection.getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonQuery.toString(), StandardCharsets.UTF_8))
                .build();
    }

    private static String getTargetUrlRequest(Query query, DbConnection connection) {
        return connection.getNodeAddress() + "/api/query/" + getEndpoint(query);
    }

    private static String getEndpoint(Query query) {
        if (query instanceof CollectionQuery)
            return "collectionQueries";
        else if (query instanceof DocumentQuery)
            return "documentQueries";
        else if (query instanceof IndexQuery)
            return "indexQueries";
        throw new UnsupportedOperationException("Unexpected query type");
    }
}
