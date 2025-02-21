package com.ruoyi.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.common.exception.UtilException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object getField(String json, String fieldName) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (Exception e) {
            throw new UtilException("Failed to parse JSON string: " + e.getMessage());
        }
        JsonNode fieldNode = jsonNode.get(fieldName);

        if (fieldNode == null) {
            throw new UtilException("Field '" + fieldName + "' does not exist in the JSON.");
        }

        if (fieldNode.isTextual()) {
            return fieldNode.asText();
        } else if (fieldNode.isInt()) {
            return fieldNode.asInt();
        } else if (fieldNode.isDouble()) {
            return fieldNode.asDouble();
        } else if (fieldNode.isBoolean()) {
            return fieldNode.asBoolean();
        }

        throw new UtilException("Field '" + fieldName + "' is of an unsupported type.");
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new UtilException("JSON字符串转换错误");
        }

    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
                throw new UtilException("JSON字符串转换错误: " + e.getMessage());
        }

    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception e) {
            throw new UtilException("JSON字符串转换错误");
        }
    }
}
