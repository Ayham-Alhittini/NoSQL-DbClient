package com.decentraldbcluster.dbclient.core.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static void loadConfigurationFromProperties() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new IllegalArgumentException("application.properties file not found in the classpath");
            }
            // Load properties from the file
            prop.load(input);
            Configuration.setApiKey( prop.getProperty("decentraldbcluster.apiKey") );
            Configuration.setToken( prop.getProperty("decentraldbcluster.token") );
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration properties", ex);
        }
    }
}
