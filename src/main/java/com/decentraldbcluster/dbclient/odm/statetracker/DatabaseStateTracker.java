package com.decentraldbcluster.dbclient.odm.statetracker;

import com.decentraldbcluster.dbclient.core.DbClient;
import com.decentraldbcluster.dbclient.odm.database.*;
import com.decentraldbcluster.dbclient.odm.statetracker.collections.CollectionManager;
import com.decentraldbcluster.dbclient.odm.statetracker.indexes.IndexManager;
import com.decentraldbcluster.dbclient.odm.statetracker.indexes.IndexOperationContext;
import com.decentraldbcluster.dbclient.util.DatabaseSubClassUtil;

import java.util.Map;
import java.util.Set;

public class DatabaseStateTracker {

    private static final Class<? extends Database> subclass = DatabaseSubClassUtil.getDatabaseSubClass();



    private static CollectionManager collectionManager;
    private static IndexManager indexManager;
    private static StateManager stateManager;


    private static void initializeManager(DbClient dbClient) {
        collectionManager = new CollectionManager(dbClient);
        indexManager = new IndexManager(dbClient);
        stateManager = new StateManager();
    }


    public static void track(DbClient dbClient) {

        initializeManager(dbClient);

        Map<String, Set<String>> previousState = stateManager.deserializeState();
        Map<String, Set<String>> currentState = stateManager.getCurrentDatabaseState(subclass);

        Set<String> addedCollections = stateManager.getNewItems(currentState.keySet(), previousState.keySet());
        Set<String> removedCollections = stateManager.getDeletedItems(currentState.keySet(), previousState.keySet());

        collectionManager.manageCollections(addedCollections, removedCollections);

        IndexOperationContext context = new IndexOperationContext(addedCollections, removedCollections, previousState, currentState);
        indexManager.manageIndexes(context);

        stateManager.serializeState(currentState);
    }
}
