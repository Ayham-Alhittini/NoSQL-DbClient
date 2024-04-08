package com.decentraldbcluster.dbclient.validation;

import com.decentraldbcluster.dbclient.actions.CollectionAction;
import com.decentraldbcluster.dbclient.exceptions.QuerySyntaxErrorException;
import com.decentraldbcluster.dbclient.queries.CollectionQuery;
import com.decentraldbcluster.dbclient.validation.schema.SchemaValidator;

import java.util.ArrayList;
import java.util.List;

public class CollectionQueryValidator {
    public static void validate(CollectionQuery query) {
        List<String> errors = new ArrayList<>();
        if (query.getCollection() == null && query.getCollectionAction() != CollectionAction.SHOW)
            errors.add("Collection name is missing");
        if (query.getCollectionAction() == null)
            errors.add("Collection action is missing");
        if (query.getSchema() != null) {
            try {
                SchemaValidator.validateSchemaDataTypesIfExists(query.getSchema());
            } catch (IllegalArgumentException ex) {
                errors.add(ex.getMessage());
            }
        }
        if (!errors.isEmpty()) {
            throw new QuerySyntaxErrorException(errors.toString());
        }
    }
}
