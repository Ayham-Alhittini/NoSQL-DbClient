package com.decentraldbcluster.dbclient.odm.statetracker.collections;

import com.decentraldbcluster.dbclient.core.DbClient;
import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.QueryFactory;

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
