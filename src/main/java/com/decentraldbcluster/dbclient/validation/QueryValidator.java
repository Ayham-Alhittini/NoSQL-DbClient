package com.decentraldbcluster.dbclient.validation;

import com.decentraldbcluster.dbclient.Query;
import com.decentraldbcluster.dbclient.queries.CollectionQuery;
import com.decentraldbcluster.dbclient.queries.DocumentQuery;
import com.decentraldbcluster.dbclient.queries.IndexQuery;

public class QueryValidator {
    public static void validate(Query query) {

        if (query instanceof CollectionQuery)
            CollectionQueryValidator.validate((CollectionQuery) query);

        else if (query instanceof DocumentQuery)
            DocumentDocumentValidator.validate((DocumentQuery) query);

        else if (query instanceof IndexQuery)
            IndexQueryValidator.validate((IndexQuery) query);

    }
}
