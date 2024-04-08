package com.decentraldbcluster.dbclient.builders;


import com.decentraldbcluster.dbclient.actions.CollectionAction;
import com.decentraldbcluster.dbclient.queries.CollectionQuery;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectionQueryBuilder implements QueryBuilder {

    private final CollectionQuery query = new CollectionQuery();
    private final ObjectMapper mapper = new ObjectMapper();


    public CollectionQueryBuilder createCollection(String collection) {
        query.setCollection(collection);
        query.setCollectionAction(CollectionAction.CREATE);
        return this;
    }

    public CollectionQueryBuilder withSchema(JsonNode schema) {
        query.setSchema(schema);
        return this;
    }
    public CollectionQueryBuilder withSchema(String schema) {
        try {
            return withSchema(mapper.readTree(schema));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CollectionQueryBuilder dropCollection(String collection) {
        query.setCollection(collection);
        query.setCollectionAction(CollectionAction.DROP);
        return this;
    }

    public CollectionQueryBuilder showCollections() {
        query.setCollectionAction(CollectionAction.SHOW);
        return this;
    }

    @Override
    public CollectionQueryBuilder collection(String collection) {
        query.setCollection(collection);
        return this;
    }

    @Override
    public CollectionQuery build() {
        return query;
    }
}
