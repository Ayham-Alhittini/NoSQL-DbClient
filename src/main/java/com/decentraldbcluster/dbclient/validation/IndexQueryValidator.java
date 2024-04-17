package com.decentraldbcluster.dbclient.validation;

import com.decentraldbcluster.dbclient.validation.exceptions.QuerySyntaxErrorException;
import com.decentraldbcluster.dbclient.query.types.IndexQuery;

import java.util.ArrayList;
import java.util.List;

public class IndexQueryValidator {
    public static void validate(IndexQuery query) {
        List<String> errors = new ArrayList<>();
        if (query.getCollection() == null) errors.add("Collection name is missing");
        if (query.getIndexAction() == null) errors.add("Index action is missing");
        if (query.getField() == null) errors.add("Field is missing");

        if (!errors.isEmpty()) {
            throw new QuerySyntaxErrorException(errors.toString());
        }
    }
}
