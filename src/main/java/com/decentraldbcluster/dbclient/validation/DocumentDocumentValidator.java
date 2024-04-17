package com.decentraldbcluster.dbclient.validation;

import com.decentraldbcluster.dbclient.query.actions.DocumentAction;
import com.decentraldbcluster.dbclient.validation.exceptions.QuerySyntaxErrorException;
import com.decentraldbcluster.dbclient.query.types.DocumentQuery;

import java.util.ArrayList;
import java.util.List;

public class DocumentDocumentValidator {
    public static void validate(DocumentQuery query) {

        List<String> errors = new ArrayList<>();

        if (query.getCollection() == null)
            errors.add("Collection name missing");

        if (query.getDocumentAction() == null)
            errors.add("Action is missing");

        validateAddDocument(query, errors);

        if (!errors.isEmpty())
            throw new QuerySyntaxErrorException(errors.toString());
    }

    private static void validateAddDocument(DocumentQuery query, List<String> errors) {
        if (query.getDocumentAction() == DocumentAction.ADD && query.getContent() == null) {
            errors.add("Content is required for add action");
        }
    }

    //for delete we need the ID

    //for update and replace we need the ID and new content






}
