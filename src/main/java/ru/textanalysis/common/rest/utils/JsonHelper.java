package ru.textanalysis.common.rest.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.textanalysis.common.rest.exception.RestCommonRuntimeException;

public final class JsonHelper {
    private static final ObjectMapper defaultObjectMapper;

    static {
        defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String writeAsString(Object o) {
        return writeAsString(o, null);
    }

    public static String writeAsString(Object o, ObjectMapper mapper) {
        try {
            return mapper == null ? defaultObjectMapper.writeValueAsString(o) : mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static ObjectMapper getJsonObjectMapper() {
        return getJsonObjectMapper(true, true);
    }

    public static ObjectMapper getJsonObjectMapper(boolean indent, boolean sort) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        if (indent) {
            jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }
        if (sort) {
            jsonObjectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
        }
        jsonObjectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return jsonObjectMapper;
    }

    public static String writeAnyClassToLog(Object o) {
        return writeClassToLog(o, o == null ? "?" : o.getClass().getName());
    }

    public static String writeClassToLog(Object o, String className) {
        if (className == null)
            throw new IllegalArgumentException("className must be not null!");
        try {
            String data = o == null ? "{ null }" : getJsonObjectMapper().writeValueAsString(o);
            return String.format("%n%s -> %s", "[B".equals(className) ? "BASE64 String" : className, data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Убирает лишнюю запятую перед закрывающей скобкой из json строки
     *
     * @param jsonConfigStr json строка
     * @return исправленная json строка
     */
    public static String fixJsonComma(String jsonConfigStr) {
        if (jsonConfigStr == null) return null;
        String result = jsonConfigStr.replaceAll(",+\\s*}", "}");
        result = result.replaceAll(",+\\s*]", "]");
        return result;
    }

    public static String toJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RestCommonRuntimeException(ex.getMessage(), ex);
        }
    }
}
