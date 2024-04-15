package com.decentraldbcluster.dbclient.odm.database;
import com.decentraldbcluster.dbclient.odm.collection.EchoCollectionFactory;
import com.decentraldbcluster.dbclient.odm.collection.EchoCollection;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public abstract class EchoDatabase {
    public EchoDatabase() {
        trackCollectionChanges();
        initializeCollections();
    }

    private void initializeCollections() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (EchoCollection.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    Type genericFieldType = field.getGenericType();
                    if (genericFieldType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
                        Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                        if (fieldArgTypes.length > 0) {
                            Class<?> fieldArgClass = (Class<?>) fieldArgTypes[0];
                            EchoCollection<?> newInstance = EchoCollectionFactory.create(field.getName(), fieldArgClass);
                            field.set(this, newInstance);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Could not initialize field: " + field.getName(), e);
                }
            }
        }
    }

    private void trackCollectionChanges() {
//        if (!Files.exists(Path.of("src/main/resources/collectionStateTracker.ser"))) {
//            for (Field field: this.getClass().getDeclaredFields()) {
//
//            }
//        }
    }

    public static Set<String> deserialize() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/collectionStateTracker.ser"))) {
            return (Set<String>) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void serialize(Object object) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/collectionStateTracker.ser"))) {
            out.writeObject(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
