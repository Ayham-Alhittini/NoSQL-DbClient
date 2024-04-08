package com.decentraldbcluster.dbclient.builders;

import com.decentraldbcluster.dbclient.Query;

public interface QueryBuilder {
    QueryBuilder collection(String collection);
    Query build();
}
