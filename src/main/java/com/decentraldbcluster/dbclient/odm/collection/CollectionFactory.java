package com.decentraldbcluster.dbclient.odm.collection;

public class CollectionFactory {
    public static <T> DbClusterCollection<T> create(String collectionName, Class<T> clazz, String IdFiledName) {
        return new DbClusterCollection<>(clazz, collectionName, IdFiledName);
    }
}
