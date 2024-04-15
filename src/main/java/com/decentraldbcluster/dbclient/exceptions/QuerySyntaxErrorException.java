package com.decentraldbcluster.dbclient.exceptions;

public class QuerySyntaxErrorException extends RuntimeException {
    public QuerySyntaxErrorException(String message) {
        super(message);
    }

}
