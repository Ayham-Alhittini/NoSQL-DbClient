package com.decentraldbcluster.dbclient.odm.statetracker.managers;

import com.decentraldbcluster.dbclient.odm.annotations.Id;
import com.decentraldbcluster.dbclient.odm.annotations.Indexed;
import com.decentraldbcluster.dbclient.odm.collection.DbClusterCollection;

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

public class StateManager {

    public static final String DATABASE_STATE_TRACKER_SER = "src/main/resources/DatabaseStateTracker.ser";

    public Map<String, Set<String>> deserializeState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATABASE_STATE_TRACKER_SER))) {
            return (Map<String, Set<String>>) in.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public void serializeState(Map<String, Set<String>> state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATABASE_STATE_TRACKER_SER))) {
            out.writeObject(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Set<String>> getCurrentDatabaseState(Class<?> clazz) {
        Map<String, Set<String>> currentDatabaseState = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (DbClusterCollection.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                Type genericFieldType = field.getGenericType();
                if (genericFieldType instanceof ParameterizedType parameterizedType) {
                    Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                    if (fieldArgTypes.length > 0) {
                        Class<?> fieldArgClass = (Class<?>) fieldArgTypes[0];
                        Set<String> indexedFields = getIndexedFieldsForCollection(fieldArgClass);
                        currentDatabaseState.put(field.getName(), indexedFields);
                    }
                }
            }
        }
        return currentDatabaseState;
    }

    private Set<String> getIndexedFieldsForCollection(Class<?> clazz) {
        Set<String> indexedFields = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Indexed.class) && !field.isAnnotationPresent(Id.class)) {
                indexedFields.add(field.getName());
            }
        }
        return indexedFields;
    }

    public Set<String> getNewItems(Set<String> current, Set<String> previous) {
        Set<String> newItems = new HashSet<>(current);
        newItems.removeAll(previous);
        return newItems;
    }

    public Set<String> getDeletedItems(Set<String> current, Set<String> previous) {
        Set<String> deletedItems = new HashSet<>(previous);
        deletedItems.removeAll(current);
        return deletedItems;
    }
}
