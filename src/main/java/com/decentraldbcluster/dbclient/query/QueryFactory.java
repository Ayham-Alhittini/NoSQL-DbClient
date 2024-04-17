package com.decentraldbcluster.dbclient.query;

import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.builders.CollectionQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.DocumentQueryBuilder;
import com.decentraldbcluster.dbclient.query.builders.IndexQueryBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class QueryFactory {

    public static Query buildSaveQuery(String collectionName, JsonNode entity, String collectionIdName) {
        JsonNode collectionId;
        if (entity instanceof ObjectNode mutableData) {
            collectionId = mutableData.remove(collectionIdName);
        } else {
            throw new RuntimeException("Invalid entity type");
        }
        if (collectionId.isNull()) {
            return QueryFactory.buildInsertQuery(collectionName, entity);
        } else {
            return QueryFactory.buildReplaceQuery(collectionName, collectionId.asText(), entity);
        }
    }

    public static Query buildInsertQuery(String collectionName, JsonNode entityData) {
        return new DocumentQueryBuilder()
                .collection(collectionName)
                .insert(entityData)
                .build();
    }

    public static Query buildReplaceQuery(String collectionName, String id, JsonNode newEntityData) {
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
