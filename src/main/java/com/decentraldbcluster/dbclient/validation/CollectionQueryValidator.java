package com.decentraldbcluster.dbclient.validation;

import com.decentraldbcluster.dbclient.query.actions.CollectionAction;
import com.decentraldbcluster.dbclient.validation.exceptions.QuerySyntaxErrorException;
import com.decentraldbcluster.dbclient.query.types.CollectionQuery;

import java.util.ArrayList;
import java.util.List;

public class CollectionQueryValidator {

    public static void validate(CollectionQuery query) {
        List<String> errors = new ArrayList<>();

        if (query.getCollection() == null && query.getCollectionAction() != CollectionAction.SHOW)
            errors.add("Collection name is missing");

        if (query.getCollectionAction() == null)
            errors.add("Collection action is missing");

        if (!errors.isEmpty()) {
            throw new QuerySyntaxErrorException(errors.toString());
        }
    }

}
