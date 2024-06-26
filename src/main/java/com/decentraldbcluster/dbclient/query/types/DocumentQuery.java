package com.decentraldbcluster.dbclient.query.types;


import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.actions.DocumentAction;
import com.fasterxml.jackson.databind.JsonNode;

public class DocumentQuery extends Query {
    private String documentId;
    private JsonNode content;
    private JsonNode condition;
    private JsonNode newContent;
    private DocumentAction documentAction;

    //------------------------- Getter And Setter

    public DocumentAction getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(DocumentAction documentAction) {
        this.documentAction = documentAction;
    }

    public JsonNode getCondition() {
        return condition;
    }

    public void setCondition(JsonNode condition) {
        this.condition = condition;
    }

    public JsonNode getContent() {
        return content;
    }

    public void setContent(JsonNode content) {
        this.content = content;
    }

    public JsonNode getNewContent() {
        return newContent;
    }

    public void setNewContent(JsonNode newContent) {
        this.newContent = newContent;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "DocumentQuery{" +
                "documentId='" + documentId + '\'' +
                ", content=" + content +
                ", condition=" + condition +
                ", newContent=" + newContent +
                ", documentAction=" + documentAction +
                ", originator='" + originator + '\'' +
                ", database='" + databaseName + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }
}
