package com.decentraldbcluster.dbclient.query.builders;

import com.decentraldbcluster.dbclient.query.actions.IndexAction;
import com.decentraldbcluster.dbclient.query.types.IndexQuery;

public class IndexQueryBuilder implements QueryBuilder {
    private final IndexQuery query = new IndexQuery();

    public IndexQueryBuilder createIndex(String field) {
        query.setField(field);
        query.setIndexAction(IndexAction.CREATE);
        return this;
    }

    public IndexQueryBuilder dropIndex(String field) {
        query.setField(field);
        query.setIndexAction(IndexAction.DROP);
        return this;
    }


    @Override
    public IndexQueryBuilder collection(String collection) {
        query.setCollection(collection);
        return this;
    }

    @Override
    public IndexQuery build() {
        return query;
    }
}
