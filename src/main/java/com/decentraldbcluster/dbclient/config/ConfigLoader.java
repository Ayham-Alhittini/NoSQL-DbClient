package com.decentraldbcluster.dbclient.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private String apiKey;
    private String apiSecret;

    public ConfigLoader() {
        loadProperties();
    }

    private void loadProperties() {
        // Using try-with-resources to ensure the input stream is closed after use
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new IllegalArgumentException("application.properties file not found in the classpath");
            }
            // Load properties from the file
            prop.load(input);
            apiKey = prop.getProperty("decentraldbcluster.apiKey");
            apiSecret = prop.getProperty("decentraldbcluster.apiSecret");
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration properties", ex);
        }
    }

    // Getters for the properties
    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }
}
