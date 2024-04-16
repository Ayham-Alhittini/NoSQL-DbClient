package com.decentraldbcluster.dbclient.odm.database;

import com.decentraldbcluster.dbclient.DbClient;
import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.annotations.Indexed;
import com.decentraldbcluster.dbclient.odm.collection.Collection;
import com.decentraldbcluster.dbclient.odm.collection.CollectionFactory;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Database {
    public Database() {
        initializeCollections();
        databaseStateTracker();
    }

    private void initializeCollections() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    Type genericFieldType = field.getGenericType();
                    if (genericFieldType instanceof ParameterizedType parameterizedType) {
                        Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                        if (fieldArgTypes.length > 0) {
                            Class<?> fieldArgClass = (Class<?>) fieldArgTypes[0];
                            String collectionId = getCollectionId(fieldArgClass);
                            Collection<?> newInstance = CollectionFactory.create(field.getName(), fieldArgClass, collectionId);
                            field.set(this, newInstance);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Could not initialize field: " + field.getName(), e);
                }
            }
        }
    }

    private String getCollectionId(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw new RuntimeException("Missing id for " + clazz.getName());
    }


    private void databaseStateTracker() {
        Map<String, Set<String>> previousState = deserialize();
        Map<String, Set<String>> currentState = getCurrentDatabaseState();

        Set<String> addedCollections = getNewItems(currentState.keySet(), previousState.keySet());
        createCollections(addedCollections);

        Set<String> removedCollections = getDeletedItems(currentState.keySet(), previousState.keySet());
        dropCollections(removedCollections);


        //loop throw the non-removed collections
        for (var collection: currentState.entrySet()) if (!removedCollections.contains(collection.getKey())) {
            String collectionName = collection.getKey();
            boolean isNewCollection = addedCollections.contains(collectionName);

            if (isNewCollection) {
                createIndexesForCollection(collectionName, collection.getValue());
            } else {
                Set<String> currentIndexes = collection.getValue();
                Set<String> previousIndexes = previousState.get( collectionName );

                Set<String> addedIndexes = getNewItems(currentIndexes, previousIndexes);
                Set<String> deletedIndexes = getDeletedItems(currentIndexes, previousIndexes);

                createIndexesForCollection(collectionName, addedIndexes);
                dropIndexesForCollection(collectionName, deletedIndexes);
            }
        }
        serialize(currentState);
    }

    private void createIndexesForCollection(String collectionName, Set<String> collectionIndexes) {
        for (String indexedField: collectionIndexes) {
            Query query = new IndexQueryBuilder()
                    .collection(collectionName)
                    .createIndex(indexedField)
                    .build();
            DbClient.getInstance().executeQuery(query);
        }
    }

    private void dropIndexesForCollection(String collectionName, Set<String> collectionIndexes) {
        for (String indexedField: collectionIndexes) {
            Query query = new IndexQueryBuilder()
                    .collection(collectionName)
                    .dropIndex(indexedField)
                    .build();
            DbClient.getInstance().executeQuery(query);
        }
    }


    private Set<String> getNewItems(Set<String> currentState, Set<String> previousState) {
        Set<String> newItems = new HashSet<>();

        for (String collection: currentState) {
            if (!previousState.contains(collection))
                newItems.add(collection);
        }
        return newItems;
    }

    private Set<String> getDeletedItems(Set<String> currentState, Set<String> previousState) {
        Set<String> deletedItems = new HashSet<>();

        for (String collection: previousState) {
            if (!currentState.contains(collection))
                deletedItems.add(collection);
        }
        return deletedItems;
    }


    private void createCollections(Set<String> collections) {
        for (String collectionName: collections) {
            Query query = new CollectionQueryBuilder()
                    .createCollection(collectionName)
                    .build();
            DbClient client = DbClient.getInstance();
            client.executeQuery(query);
        }
    }

    private void dropCollections(Set<String> collections) {
        for (String collectionName: collections) {
            Query query = new CollectionQueryBuilder()
                    .dropCollection(collectionName)
                    .build();
            DbClient client = DbClient.getInstance();
            client.executeQuery(query);
        }
    }

    private Map<String, Set<String>> getCurrentDatabaseState() {
        Map<String, Set<String>> currentDatabaseState = new HashMap<>();

        for (Field databaseField : this.getClass().getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(databaseField.getType())) {
                Type genericFieldType = databaseField.getGenericType();
                if (genericFieldType instanceof ParameterizedType parameterizedType) {
                    Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                    if (fieldArgTypes.length > 0) {
                        Class<?> fieldArgClass = (Class<?>) fieldArgTypes[0];
                        Set<String> indexedFields = getIndexedFieldsForCollection(fieldArgClass);
                        currentDatabaseState.put(databaseField.getName(), indexedFields);
                    }
                }
            }
        }
        return currentDatabaseState;
    }
    private Set<String> getIndexedFieldsForCollection(Class<?> clazz) {
        Set<String> indexedFields = new HashSet<>();
        for (Field collectionFields: clazz.getDeclaredFields())
            if (collectionFields.isAnnotationPresent(Indexed.class) && !collectionFields.isAnnotationPresent(Id.class))
                    indexedFields.add(collectionFields.getName());
        return indexedFields;
    }

    private Map<String, Set<String>> deserialize() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/DatabaseStateTracker.ser"))) {
            return (Map<String, Set<String>>) in.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private void serialize(Map<String, Set<String>> databaseStateTracker) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/DatabaseStateTracker.ser"))) {
            out.writeObject(databaseStateTracker);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
