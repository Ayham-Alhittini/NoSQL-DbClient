package com.decentraldbcluster.dbclient.response;

public class QueryResponse {
    private final int status;
    private String error;
    private String result;

    public QueryResponse(int status, String response) {
        this.status = status;
        setResponse(response);
    }

    private void setResponse(String response) {
        if (status == 200)
            result = response;
        else
            error = response;
    }

    public boolean isSucceed() {
        return status == 200;
    }

    public int getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }


    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "QueryResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
