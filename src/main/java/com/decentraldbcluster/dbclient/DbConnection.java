package com.decentraldbcluster.dbclient;

import com.decentraldbcluster.dbclient.apikey.ApiKeyDecryption;

public class DbConnection {
    private String nodeAddress;
    private String database;
    private String originator;
    private String token;

    public DbConnection(String apiKey, String apiSecret) {
        var keyData = ApiKeyDecryption.decryptApiKey(apiKey);
        this.nodeAddress = keyData[0];
        this.database = keyData[1];
        this.originator = keyData[2];
        this.token = apiSecret;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getToken() {
        return "Bearer " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
