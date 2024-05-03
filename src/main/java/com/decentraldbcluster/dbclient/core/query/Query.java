package com.decentraldbcluster.dbclient.core.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Query {
    @JsonProperty
    protected String originator;
    @JsonProperty
    protected String databaseName;
    @JsonProperty
    protected String collection;

    public String getCollection() {
        return collection;
    }

    protected void setOriginator(String originator) {
        this.originator = originator;
    }

    protected void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
