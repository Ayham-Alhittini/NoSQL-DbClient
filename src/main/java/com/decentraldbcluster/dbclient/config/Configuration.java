package com.decentraldbcluster.dbclient.config;

public class Configuration {
    private static String apiKey;
    private static String token;

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        Configuration.apiKey = apiKey;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Configuration.token = token;
    }

}
