package com.decentraldbcluster.dbclient.odm.database;

import java.util.Map;
import java.util.Set;

public abstract class Database {
    private final CollectionManager collectionManager = new CollectionManager();
    private final IndexManager indexManager = new IndexManager();
    private final StateManager stateManager = new StateManager();

    public Database() {
        initializeCollections();
        databaseStateTracker();
    }

    private void initializeCollections() {
        collectionManager.initializeCollections(this);
    }

    private void databaseStateTracker() {
        Map<String, Set<String>> previousState = stateManager.deserializeState();
        Map<String, Set<String>> currentState = stateManager.getCurrentDatabaseState(this.getClass());

        Set<String> addedCollections = stateManager.getNewItems(currentState.keySet(), previousState.keySet());
        Set<String> removedCollections = stateManager.getDeletedItems(currentState.keySet(), previousState.keySet());

        collectionManager.manageCollections(addedCollections, removedCollections);

        IndexOperationContext context = new IndexOperationContext(addedCollections, removedCollections, previousState, currentState);
        indexManager.manageIndexes(context);

        stateManager.serializeState(currentState);
    }
}
