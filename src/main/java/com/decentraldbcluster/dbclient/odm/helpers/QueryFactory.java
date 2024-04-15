package com.decentraldbcluster.dbclient.odm.helpers;

import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.query.builders.DocumentQueryBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class QueryFactory {
    public static Query buildInsertQuery(String collectionName, JsonNode entityData, String collectionId) {
        if (entityData instanceof ObjectNode mutableData) {
            JsonNode collectionIdTextNode = mutableData.get(collectionId);
            mutableData.remove(collectionId);
            mutableData.put("object_id", collectionIdTextNode.asText());
        }

        return new DocumentQueryBuilder()
                .collection(collectionName)
                .insert(entityData)
                .build();
    }

    //Todo: Should be handled as well
    public static Query buildReplaceQuery(String collectionName, String id, JsonNode newEntityData) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .replace(id, newEntityData)
                .build();
    }

    public static Query buildFindAllQuery(String collectionName) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .findAll()
                .build();
    }

    public static Query buildFindByIdQuery(String collectionName, String id) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .findById(id)
                .build();
    }

    public static Query buildDeleteByIdQuery(String collectionName, String id) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .delete(id)
                .build();
    }
}
