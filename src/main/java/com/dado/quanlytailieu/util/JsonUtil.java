package com.dado.quanlytailieu.util;

import com.dado.quanlytailieu.config.Default;
import com.dado.quanlytailieu.exception.JsonException;
import lombok.experimental.UtilityClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        Default.setDefault(MAPPER);
    }

    public static final TypeReference<List<Integer>> TYPE_LIST_INTEGER = new TypeReference<>() {};
    public static final TypeReference<Map<String, Integer>> TYPE_MAP_STRING_INTEGER =
            new TypeReference<>() {};
    public static final TypeReference<List<Map<String, String>>> TYPE_LIST_MAP_STRING_STRING =
            new TypeReference<>() {};
    public static final TypeReference<Map<String, String>> TYPE_MAP_STRING_STRING =
            new TypeReference<>() {};

    public static List<Integer> readListInteger(String str) {
        if (str.isBlank()) {
            return Collections.emptyList();
        }
        return readObject(str, TYPE_LIST_INTEGER);
    }

    public static Map<String, Integer> readMapStringInteger(String str) {
        if (str.isBlank()) {
            return Collections.emptyMap();
        }
        return readObject(str, TYPE_MAP_STRING_INTEGER);
    }

    public static <T> T readObject(String str, TypeReference<T> typeRef) {
        try {
            return MAPPER.readValue(str, typeRef);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}
