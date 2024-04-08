package com.decentraldbcluster.dbclient.validation.schema;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class SchemaValidator {

    public static void validateSchemaDataTypesIfExists(JsonNode schema) {
        if (schema == null || schema.isNull()) return;

        List<String> errors = new ArrayList<>();
        validateSchemaWithPath(schema, "schema", errors);

        if (!errors.isEmpty())
            throw new IllegalArgumentException(errors.toString());
    }


    private static void validateSchemaWithPath(JsonNode schema, String path, List<String> errors) {
        if (!schema.isObject()) {
            validateDataType(schema, path, errors);
            return;
        }

        schema.fields().forEachRemaining(field ->
                validateSchemaWithPath(field.getValue(), path + "." + field.getKey(), errors));
    }

    private static void validateDataType(JsonNode schema, String path, List<String> errors) {
        String fieldValue = schema.asText().toUpperCase();
        try {
            AppDataType type = AppDataType.valueOf(fieldValue);
            if (type == AppDataType.OBJECT) {
                errors.add(path + ", OBJECT type is not valid here.");
            }
        } catch (IllegalArgumentException e) {
            errors.add(path + ", invalid data type [" + fieldValue + "]");
        }
    }

}
