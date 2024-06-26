package com.decentraldbcluster.dbclient.response;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseHandler {
    private final ObjectMapper mapper;

    public ResponseHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T parseResponse(QueryResponse response, JavaType type, String collectionIdName) {
        if (response.isSucceed()) {
            try {
                String json = response.getResult().replace("\"object_id\"", "\""+collectionIdName+"\"");
                return mapper.readValue(json, type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Failed to parse response: " + response.getError());
    }
}
