package com.decentraldbcluster.dbclient.validation.exceptions;

public class QuerySyntaxErrorException extends RuntimeException {
    public QuerySyntaxErrorException(String message) {
        super(message);
    }

}
