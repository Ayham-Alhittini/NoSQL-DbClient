package com.decentraldbcluster.dbclient.query;

import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.DocumentQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class QueryFactory {

    public static Query buildInsertQuery(String collectionName, JsonNode entityData, String collectionIdName) {

        JsonNode object_id = null;
        if (entityData instanceof ObjectNode objectNode) {
            object_id = objectNode.remove(collectionIdName);
        }
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .insert(entityData)
                .withId(object_id == null || object_id.isNull() ? null : object_id.asText())
                .build();
    }

    public static Query buildReplaceQuery(String collectionName, JsonNode newEntityData, String collectionIdName) {
        String id = null;
        if (newEntityData instanceof ObjectNode objectNode) {
            id = objectNode.remove(collectionIdName).asText();
        }
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .replace(id, newEntityData)
                .build();
    }

    public static Query buildFindAllQuery(String collectionName, JsonNode filter) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .findAll()
                .where(filter)
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

    public static Query buildCreateCollectionQuery(String collectionName) {
        return new CollectionQueryBuilder()
                .createCollection(collectionName)
                .build();
    }
    public static Query buildDropCollectionQuery(String collectionName) {
        return new CollectionQueryBuilder()
                .dropCollection(collectionName)
                .build();
    }

    public static Query buildCreateIndexQuery(String collectionName, String indexedFiled) {
        return new IndexQueryBuilder()
                .collection(collectionName)
                .createIndex(indexedFiled)
                .build();
    }
    public static Query buildDropIndexQuery(String collectionName, String indexedFiled) {
        return new IndexQueryBuilder()
                .collection(collectionName)
                .dropIndex(indexedFiled)
                .build();
    }
}
