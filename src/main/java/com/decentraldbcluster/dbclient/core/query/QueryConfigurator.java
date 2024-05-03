package com.decentraldbcluster.dbclient.core.query;

import com.decentraldbcluster.dbclient.core.db.DbConnection;

public class QueryConfigurator {
    public static void configure(Query query, DbConnection connection) {
        query.setOriginator(connection.getOriginator());
        query.setDatabaseName(connection.getDatabase());
    }
}
