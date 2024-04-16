package com.decentraldbcluster.dbclient.query.builders;

import com.decentraldbcluster.dbclient.core.Query;

public interface QueryBuilder {
    QueryBuilder collection(String collection);
    Query build();
}
