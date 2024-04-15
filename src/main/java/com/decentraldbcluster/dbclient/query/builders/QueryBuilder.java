package com.decentraldbcluster.dbclient.query.builders;

import com.decentraldbcluster.dbclient.Query;

public interface QueryBuilder {
    QueryBuilder collection(String collection);
    Query build();
}
