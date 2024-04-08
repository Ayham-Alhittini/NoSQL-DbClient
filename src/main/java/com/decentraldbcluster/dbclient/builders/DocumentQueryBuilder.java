package com.decentraldbcluster.dbclient.builders;

import com.decentraldbcluster.dbclient.actions.DocumentAction;
import com.decentraldbcluster.dbclient.queries.DocumentQuery;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocumentQueryBuilder implements QueryBuilder {
    private final DocumentQuery query = new DocumentQuery();
    private final ObjectMapper mapper = new ObjectMapper();


    public DocumentQueryBuilder findById(String documentId) {
        query.setDocumentAction(DocumentAction.SELECT);
        query.setDocumentId(documentId);
        return this;
    }

    public DocumentQueryBuilder where(JsonNode condition) {
        query.setCondition(condition);
        return this;
    }

    public DocumentQueryBuilder where(String condition) {
        try {
            return where( mapper.readTree(condition) );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DocumentQueryBuilder insert(JsonNode content) {
        query.setDocumentAction(DocumentAction.ADD);
        query.setContent(content);
        return this;
    }

    public DocumentQueryBuilder insert(String content) {
        try {
            return insert( mapper.readTree(content) );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DocumentQueryBuilder delete(String documentId) {
        query.setDocumentAction(DocumentAction.DELETE);
        query.setDocumentId(documentId);
        return this;
    }

    public DocumentQueryBuilder update(String documentId, JsonNode newContent) {
        query.setDocumentAction(DocumentAction.UPDATE);
        query.setDocumentId(documentId);
        query.setNewContent(newContent);
        return this;
    }

    public DocumentQueryBuilder update(String documentId, String newContent) {
        try {
            return update(documentId, mapper.readTree(newContent));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DocumentQueryBuilder replace(String documentId, JsonNode newContent) {
        query.setDocumentAction(DocumentAction.REPLACE);
        query.setDocumentId(documentId);
        query.setNewContent(newContent);
        return this;
    }

    public DocumentQueryBuilder replace(String documentId, String newContent) {
        try {
            return replace(documentId, mapper.readTree(newContent));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //Rely on condition
    public DocumentQueryBuilder select() {
        query.setDocumentAction(DocumentAction.SELECT);
        return this;
    }

    @Override
    public DocumentQueryBuilder collection(String collection) {
        query.setCollection(collection);
        return this;
    }

    @Override
    public DocumentQuery build() {
        return query;
    }
}
