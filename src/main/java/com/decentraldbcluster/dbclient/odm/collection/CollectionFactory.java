package com.decentraldbcluster.dbclient.odm.collection;

public class CollectionFactory {
    public static <T> Collection<T> create(String collectionName, Class<T> clazz, String IdFiledName) {
        return new Collection<>(clazz, collectionName, IdFiledName);
    }
}
