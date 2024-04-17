package com.decentraldbcluster.dbclient.odm.database;

import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.collection.Collection;
import com.decentraldbcluster.dbclient.odm.collection.CollectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DatabaseInitializer {
    public static void initialize(Object databaseInstance) {
        Class<?> clazz = databaseInstance.getClass();
        Field[] fields = clazz.getDeclaredFields();
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
                            field.set(databaseInstance, newInstance);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Could not initialize field: " + field.getName(), e);
                }
            }
        }
    }

    private static String getCollectionId(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw new RuntimeException("Missing id for " + clazz.getName());
    }
}
