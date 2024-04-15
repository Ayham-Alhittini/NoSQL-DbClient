package com.decentraldbcluster.dbclient.odm.collection;

import com.decentraldbcluster.dbclient.odm.collection.EchoCollection;

public class EchoCollectionFactory {
    public static <T> EchoCollection<T> create(String collectionName, Class<T> clazz) {
        return new EchoCollection<>(clazz, collectionName);
    }
}
