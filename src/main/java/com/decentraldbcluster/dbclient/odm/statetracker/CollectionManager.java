package com.decentraldbcluster.dbclient.odm.statetracker;

import com.decentraldbcluster.dbclient.core.DbClient;
import com.decentraldbcluster.dbclient.core.Query;
import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.collection.Collection;
import com.decentraldbcluster.dbclient.odm.collection.CollectionFactory;
import com.decentraldbcluster.dbclient.query.QueryFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

public class CollectionManager {
    private final DbClient dbClient = DbClient.getInstance();

    public void manageCollections(Set<String> added, Set<String> removed) {
        for (String collectionName : added) {
            Query query = QueryFactory.buildCreateCollectionQuery(collectionName);
            dbClient.executeQuery(query);
        }
        for (String collectionName : removed) {
            Query query = QueryFactory.buildDropCollectionQuery(collectionName);
            dbClient.executeQuery(query);
        }
    }
}
