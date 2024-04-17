package com.decentraldbcluster.dbclient.core.db;

import com.decentraldbcluster.dbclient.util.DataEncryptor;

public class DbConnection {
    private final String nodeAddress;
    private final String database;
    private final String originator;
    private final String token;

    public DbConnection(String apiKey, String apiSecret) {
        try {
            var keyData = DataEncryptor.decryptApiKey(apiKey);
            this.nodeAddress = keyData[0];
            this.database = keyData[1];
            this.originator = keyData[2];
            this.token = apiSecret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getOriginator() {
        return originator;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public String getDatabase() {
        return database;
    }

    public String getToken() {
        return "Bearer " + token;
    }

}
