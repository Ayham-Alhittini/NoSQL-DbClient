package com.decentraldbcluster.dbclient.odm.statetracker;

import com.decentraldbcluster.dbclient.odm.database.*;
import com.decentraldbcluster.dbclient.util.DatabaseSubClassUtil;

import java.util.Map;
import java.util.Set;

public class DatabaseStateTracker {

    private static final Class<? extends Database> subclass = DatabaseSubClassUtil.getDatabaseSubClass();



    private static final CollectionManager collectionManager = new CollectionManager();
    private static final IndexManager indexManager = new IndexManager();
    private static final StateManager stateManager = new StateManager();



    public static void track() {
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
