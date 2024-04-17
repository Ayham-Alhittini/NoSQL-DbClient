package com.decentraldbcluster.dbclient.odm.statetracker.indexes;

import java.util.Map;
import java.util.Set;

public class IndexOperationContext {
    Set<String> addedCollections;
    Set<String> removedCollections;
    Map<String, Set<String>> previousState;
    Map<String, Set<String>> currentState;

    public IndexOperationContext(Set<String> addedCollections, Set<String> removedCollections,
                                 Map<String, Set<String>> previousState, Map<String, Set<String>> currentState) {
        this.addedCollections = addedCollections;
        this.removedCollections = removedCollections;
        this.previousState = previousState;
        this.currentState = currentState;
    }
}
