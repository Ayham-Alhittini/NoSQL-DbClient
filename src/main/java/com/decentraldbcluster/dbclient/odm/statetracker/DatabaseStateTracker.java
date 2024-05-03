package com.decentraldbcluster.dbclient.odm.statetracker;

import com.decentraldbcluster.dbclient.odm.database.DbClusterDatabase;
import com.decentraldbcluster.dbclient.odm.statetracker.managers.CollectionManager;
import com.decentraldbcluster.dbclient.odm.statetracker.managers.IndexManager;
import com.decentraldbcluster.dbclient.odm.statetracker.dataobject.IndexDataObject;
import com.decentraldbcluster.dbclient.odm.statetracker.managers.StateManager;

import java.util.Map;
import java.util.Set;

public class DatabaseStateTracker {


    private static final CollectionManager collectionManager = new CollectionManager();
    private static final IndexManager indexManager = new IndexManager();
    private static final StateManager stateManager = new StateManager();



    public static void track(Class<? extends DbClusterDatabase> subclass) {

        Map<String, Set<String>> previousState = stateManager.deserializeState();
        Map<String, Set<String>> currentState = stateManager.getCurrentDatabaseState(subclass);

        Set<String> addedCollections = stateManager.getNewItems(currentState.keySet(), previousState.keySet());
        Set<String> removedCollections = stateManager.getDeletedItems(currentState.keySet(), previousState.keySet());

        collectionManager.manageCollections(addedCollections, removedCollections);

        IndexDataObject indexDataObject = new IndexDataObject(addedCollections, removedCollections, previousState, currentState);
        indexManager.manageIndexes(indexDataObject);

        stateManager.serializeState(currentState);
    }
}
