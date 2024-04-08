package com.decentraldbcluster.dbclient.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Document implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private JsonNode content;
    private int version = 1;

    public Document() {}

    public Document(JsonNode data, int nodeNumber) {
        this.content = data;
        id = UUID.randomUUID().toString() + nodeNumber;
    }

    public Document(Document src) {
        this.id = src.id;
        this.content = src.getContent();
        this.version = src.getVersion();
    }

    public String getId() {
        return id;
    }

    public JsonNode getContent() {
        return content;
    }

    public void setContent(JsonNode content) {
        this.content = content;
    }

    public int getVersion() {
        return version;
    }

    public void incrementVersion() {
        this.version++;
    }

    @JsonIgnore
    public int getAffinityPort() {
        int basePort = 8080;
        // The last digit in the ID represent the node number
        int nodeNumber = id.charAt(id.length() - 1) - '0';
        return basePort + nodeNumber;
    }

}