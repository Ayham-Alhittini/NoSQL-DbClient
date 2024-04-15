package com.decentraldbcluster.dbclient.odm.collection;

public class EchoCollectionFactory {
    public static <T> EchoCollection<T> create(String collectionName, Class<T> clazz, String IdFiledName) {
        return new EchoCollection<>(clazz, collectionName, IdFiledName);
    }
}
