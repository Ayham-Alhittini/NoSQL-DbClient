package com.decentraldbcluster.dbclient.odm.statetracker.dataobject;

import java.util.Map;
import java.util.Set;

public class IndexDataObject {
    public Set<String> addedCollections;
    public Set<String> removedCollections;
    public Map<String, Set<String>> previousState;
    public Map<String, Set<String>> currentState;

    public IndexDataObject(Set<String> addedCollections, Set<String> removedCollections,
                           Map<String, Set<String>> previousState, Map<String, Set<String>> currentState) {
        this.addedCollections = addedCollections;
        this.removedCollections = removedCollections;
        this.previousState = previousState;
        this.currentState = currentState;
    }
}
