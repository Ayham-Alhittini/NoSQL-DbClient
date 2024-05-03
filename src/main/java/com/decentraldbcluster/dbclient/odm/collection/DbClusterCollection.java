package com.decentraldbcluster.dbclient.odm.collection;

import com.decentraldbcluster.dbclient.core.DbClient;
import com.decentraldbcluster.dbclient.core.query.Query;
import com.decentraldbcluster.dbclient.query.QueryFactory;
import com.decentraldbcluster.dbclient.response.QueryResponse;
import com.decentraldbcluster.dbclient.response.ResponseHandler;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class DbClusterCollection<Entity> {

    private final DbClient dbClient = DbClient.getInstance();
    private final ObjectMapper mapper = ObjectMapperConfigurator.configureMapper();
    private final ResponseHandler responseHandler = new ResponseHandler(mapper);

    private final String collectionName;
    private final JavaType entityType;
    private final JavaType singleEntityType;
    private final String collectionIdName;

    public DbClusterCollection(Class<Entity> clazz, String collectionName, String collectionId) {
        this.collectionName = collectionName;
        this.entityType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        this.singleEntityType = mapper.getTypeFactory().constructType(clazz);
        this.collectionIdName = collectionId;
    }

    public Entity save(Entity entity) {

        JsonNode entityNode = mapper.valueToTree(entity);
        Query query = isInsertRequest(entityNode) ? QueryFactory.buildInsertQuery(collectionName, entityNode, collectionIdName) :
                QueryFactory.buildReplaceQuery(collectionName, entityNode, collectionIdName);

        QueryResponse response = dbClient.executeQuery(query);
        return responseHandler.parseResponse(response, singleEntityType, collectionIdName);
    }

    public void saveAll(List<Entity> entities) {
        for (Entity entity: entities)
            save(entity);
    }

    private boolean isInsertRequest(JsonNode entityNode) {
        if (entityNode.get(collectionIdName).isNull()) {
            return true;
        }

        String id = entityNode.get(collectionIdName).asText();
        return findById(id) == null;
    }


    public List<Entity> findAll(JsonNode jsonNode) {
        Query query = QueryFactory.buildFindAllQuery(collectionName, jsonNode);
        QueryResponse response = dbClient.executeQuery(query);
        return responseHandler.parseResponse(response, entityType, collectionIdName);
    }

    public List<Entity> findAll() {
        Query query = QueryFactory.buildFindAllQuery(collectionName, null);
        QueryResponse response = dbClient.executeQuery(query);
        return responseHandler.parseResponse(response, entityType, collectionIdName);
    }

    public List<Entity> findAll(Map<String, Object> filter) {
        try {
            return findAll((JsonNode) mapper.valueToTree(filter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Entity> findBy(String fieldName, Object value) {
        JsonNode valueNode = mapper.valueToTree(value);
        JsonNode filterNode = mapper.createObjectNode().put(fieldName, valueNode.asText());
        return findAll(filterNode);
    }

    public Entity findFirstBy(String fieldName, Object value) {
        List<Entity> result = findBy(fieldName, value);

        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    public Entity findById(String id) {
        Query query = QueryFactory.buildFindByIdQuery(collectionName, id);
        QueryResponse response = dbClient.executeQuery(query);
        if (response.isSucceed()) {
            return responseHandler.parseResponse(response, singleEntityType, collectionIdName);
        }
        return null;
    }

    public boolean deleteById(String id) {
        Query query = QueryFactory.buildDeleteByIdQuery(collectionName, id);
        QueryResponse response = dbClient.executeQuery(query);
        return response.isSucceed();
    }
}
