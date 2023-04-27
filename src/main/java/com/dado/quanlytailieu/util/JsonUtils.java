package com.dado.quanlytailieu.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static String toString(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public static <T> T toObject(String str, Class<T> type) {
        try {
            return MAPPER.readValue(str, type);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public static <T> T toObject(String str, TypeReference<T> typeRef) {
        try {
            return MAPPER.readValue(str, typeRef);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public Map<String, Object> toMap(Object object) {
        return MAPPER.convertValue(object, new TypeReference<>() {});
    }

    public static JsonNode toJsonNode(Object obj) {
        if (obj instanceof String json) {
            try {
                return MAPPER.readTree(json);
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
        return MAPPER.valueToTree(obj);
    }
}
