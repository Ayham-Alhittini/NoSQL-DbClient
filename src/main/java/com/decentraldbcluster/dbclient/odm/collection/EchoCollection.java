package com.decentraldbcluster.dbclient.odm.collection;

import com.decentraldbcluster.dbclient.DbClient;
import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.odm.helpers.ObjectMapperConfigurator;
import com.decentraldbcluster.dbclient.odm.helpers.QueryFactory;
import com.decentraldbcluster.dbclient.odm.helpers.ResponseHandler;
import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class EchoCollection<Entity> {

    private final DbClient dbClient = DbClient.getInstance();
    private final ObjectMapper mapper = ObjectMapperConfigurator.configureMapper();
    private final ResponseHandler responseHandler = new ResponseHandler(mapper);

    private final String collectionName;
    private final JavaType entityType;
    private final JavaType singleEntityType;
    private final String collectionId;

    public EchoCollection(Class<Entity> clazz, String collectionName, String collectionId) {
        this.collectionName = collectionName;
        this.entityType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        this.singleEntityType = mapper.getTypeFactory().constructType(clazz);
        this.collectionId = collectionId;
    }

    public Entity save(Entity entity) {
        JsonNode json = mapper.valueToTree(entity);
        Query query;
        if (json.get(collectionId).isNull()) {
            query = QueryFactory.buildInsertQuery(collectionName, mapper.valueToTree(entity), collectionId);
        } else {
            query = QueryFactory.buildReplaceQuery(collectionName, json.get(collectionId).asText(), json);
        }
        QueryResponse response = dbClient.executeQuery(query);
        return responseHandler.parseResponse(response, singleEntityType, collectionId);
    }

    public List<Entity> findAll() {
        Query query = QueryFactory.buildFindAllQuery(collectionName);
        QueryResponse response = dbClient.executeQuery(query);
        return responseHandler.parseResponse(response, entityType, collectionId);
    }

    public Optional<Entity> findById(String id) {
        Query query = QueryFactory.buildFindByIdQuery(collectionName, id);
        QueryResponse response = dbClient.executeQuery(query);
        if (response.isSucceed()) {
            return Optional.of(responseHandler.parseResponse(response, singleEntityType, collectionId));
        }
        return Optional.empty();
    }

    public boolean deleteById(String id) {
        Query query = QueryFactory.buildDeleteByIdQuery(collectionName, id);
        QueryResponse response = dbClient.executeQuery(query);
        return response.isSucceed();
    }
}
