package com.booking.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class JsonUtils {
    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules();

        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
    }

    public static ObjectNode newObjectNode() {
        return mapper.createObjectNode();
    }

    public static ArrayNode newArrayNode() {
        return mapper.createArrayNode();
    }

    public static List<String> getKeys(String json) throws JsonProcessingException {
        List<String> keys = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(json);
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(keys::add);
        return keys;
    }

    public static List<String> getKeys(JsonNode jsonNode) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(keys::add);
        return keys;
    }

    public static ArrayNode fromListToArrayNode(List<String> list) {
        ArrayNode arrayNode = mapper.createArrayNode();
        list.forEach(arrayNode::add);
        return arrayNode;
    }

    public static List<String> fromArrayNodeToList(ArrayNode arrayNode) {
        List<String> stringList = new ArrayList<>();

        for (JsonNode jsonNode : arrayNode) {
            stringList.add(jsonNode.asText());
        }
        return stringList;
    }

    public static String toString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T getObject(String data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> T getObjectWithException(String data, Class<T> clazz) throws Exception {

        return mapper.readValue(data, clazz);
    }


    public static <T> T getObject(Map<String, Object> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObjectFromMap(Map<String, String> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(byte[] data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(InputStream inputStream, Class<T> clazz) {
        try {
            return mapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getMap(String jsonData) {
        try {
            return mapper.readValue(jsonData, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static <T> List<T> formMapToListObject(String jsonData, Class<T> clazz) {
        try {
            return getMap(jsonData).values()
                    .stream()
                    .map(o -> mapper.convertValue(o, clazz))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> List<T> formMapToListObject(JsonNode jsonNode, Class<T> clazz) {
        try {
            Map<String, T> map = mapper.convertValue(jsonNode, new TypeReference<>() {
            });
            return map.values()
                    .stream()
                    .map(o -> mapper.convertValue(o, clazz))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> elementType) {
        try {
            JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, elementType);
            return mapper.readValue(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> List<T> mapJsonToList(Map<String, Object> map, Class<T> clazz) {
        return map.keySet()
                .stream()
                .map(key -> getObject(toString(map.get(key)), clazz))
                .toList();
    }

    public static <T> T getObject(String json, TypeReference typeRef) throws JsonProcessingException {
        try {
            return (T) mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw e;
        }
    }
}
