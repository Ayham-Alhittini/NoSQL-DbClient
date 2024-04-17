package com.decentraldbcluster.dbclient.core.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Query {
    @JsonProperty
    protected String originator;
    @JsonProperty
    protected String database;
    @JsonProperty
    protected String collection;

    public String getCollection() {
        return collection;
    }

    protected void setOriginator(String originator) {
        this.originator = originator;
    }

    protected void setDatabase(String database) {
        this.database = database;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
