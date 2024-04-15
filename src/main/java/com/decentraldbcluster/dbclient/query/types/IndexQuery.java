package com.decentraldbcluster.dbclient.query.types;

import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.actions.IndexAction;

public class IndexQuery extends Query {
    private IndexAction indexAction;
    private String field;


    public IndexAction getIndexAction() {
        return indexAction;
    }

    public void setIndexAction(IndexAction indexAction) {
        this.indexAction = indexAction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "IndexQuery{" +
                "indexAction=" + indexAction +
                ", field='" + field + '\'' +
                ", originator='" + originator + '\'' +
                ", database='" + database + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }
}
