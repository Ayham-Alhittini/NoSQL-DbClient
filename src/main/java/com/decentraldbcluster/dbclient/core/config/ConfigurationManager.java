package com.decentraldbcluster.dbclient.core.config;

public class ConfigurationManager {
    static {
        ConfigLoader.loadConfigurationFromProperties();
    }

    public static String getApiKey() {
        return Configuration.getApiKey();
    }

    public static String getToken() {
        return Configuration.getToken();
    }
}
