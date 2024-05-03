package com.decentraldbcluster.dbclient.core;

import com.decentraldbcluster.dbclient.core.config.ConfigurationManager;
import com.decentraldbcluster.dbclient.core.db.DbConnection;
import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.core.query.QueryExecutor;
import com.decentraldbcluster.dbclient.response.QueryResponse;

public class DbClient {

    private static DbClient instance;
    private final QueryExecutor queryExecutor;

    private DbClient() {
        DbConnection connection = new DbConnection(ConfigurationManager.getApiKey(), ConfigurationManager.getToken());
        queryExecutor = new QueryExecutor(connection);
    }

    public static DbClient getInstance() {
        if (instance == null) {
            instance = new DbClient();
        }
        return instance;
    }

    public QueryResponse executeQuery(Query query) {
        return queryExecutor.executeQuery(query);
    }
}
