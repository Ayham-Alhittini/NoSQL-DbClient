package com.decentraldbcluster.dbclient.odm.collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperConfigurator {
    public static ObjectMapper configureMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Do not fail on unknown properties in JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Set visibility rules for the mapper
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Register the JavaTimeModule to handle Java 8 date-time API
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
