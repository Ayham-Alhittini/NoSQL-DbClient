package com.decentraldbcluster.dbclient.response;

import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseHandler {
    private final ObjectMapper mapper;

    public ResponseHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T parseResponse(QueryResponse response, JavaType type, String collectionId) {
        if (response.isSucceed()) {
            try {
                String json = response.getResult().replace("\"object_id\"", "\""+collectionId+"\"");
                return mapper.readValue(json, type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Failed to parse response: " + response.getError());
    }
}
