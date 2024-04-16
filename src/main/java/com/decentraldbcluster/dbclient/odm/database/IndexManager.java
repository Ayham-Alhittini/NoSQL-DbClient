package com.decentraldbcluster.dbclient.odm.database;

import com.decentraldbcluster.dbclient.DbClient;
import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.QueryFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IndexManager {
    private final DbClient dbClient = DbClient.getInstance();

    public void manageIndexes(IndexOperationContext context) {
        createIndexesForNewCollections(context.addedCollections, context.currentState);
        updateIndexesForExistingCollections(context);
    }

    private void createIndexesForNewCollections(Set<String> addedCollections, Map<String, Set<String>> currentState) {
        for (String collectionName : addedCollections) {
            Set<String> indexes = currentState.get(collectionName);
            if (indexes != null) {
                for (String index : indexes) {
                    createIndex(collectionName, index);
                }
            }
        }
    }

    private void updateIndexesForExistingCollections(IndexOperationContext context) {
        for (String collectionName : context.currentState.keySet()) {
            if (!context.addedCollections.contains(collectionName) && !context.removedCollections.contains(collectionName)) {
                Set<String> currentIndexes = context.currentState.get(collectionName);
                Set<String> previousIndexes = context.previousState.get(collectionName);

                Set<String> addedIndexes = getNewItems(currentIndexes, previousIndexes);
                Set<String> deletedIndexes = getDeletedItems(currentIndexes, previousIndexes);

                for (String index : addedIndexes) {
                    createIndex(collectionName, index);
                }
                for (String index : deletedIndexes) {
                    dropIndex(collectionName, index);
                }
            }
        }
    }

    private void createIndex(String collectionName, String indexedField) {
        Query query = QueryFactory.buildCreateIndexQuery(collectionName, indexedField);
        dbClient.executeQuery(query);
    }

    private void dropIndex(String collectionName, String indexedField) {
        Query query = QueryFactory.buildDropIndexQuery(collectionName, indexedField);
        dbClient.executeQuery(query);
    }

    private Set<String> getNewItems(Set<String> current, Set<String> previous) {
        Set<String> newItems = new HashSet<>(current);
        newItems.removeAll(previous);
        return newItems;
    }

    private Set<String> getDeletedItems(Set<String> current, Set<String> previous) {
        Set<String> deletedItems = new HashSet<>(previous);
        deletedItems.removeAll(current);
        return deletedItems;
    }
}
