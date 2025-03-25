import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import annotations.*;
import exceptions.JsonSerializationException;

public class ObjectToJsonConverter {
    public String convertToJson(Object object) throws JsonSerializationException {
        try {

            checkIfSerializable(object);
            initializeObject(object);
            return getJsonString(object);

        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }
    private void checkIfSerializable(Object object) {
        if(object == null) {
            throw new JsonSerializationException("null");
        }
        Class<?> clazz = object.getClass();
        if(!clazz.isAnnotationPresent(JsonSerializable.class)){
            throw new JsonSerializationException("not possible to jsonserialize");
        }

    }

    private void initializeObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Init.class))
                .forEach(method -> {
                    method.setAccessible(true);
                    try {
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    private String getJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(JsonElement.class))
                .forEach(field -> {
                    try {
                        jsonElementsMap.put(getKey(field),(String) field.get(object));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });


        String jsonString = jsonElementsMap.entrySet()
            .stream()
            .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
            .collect(Collectors.joining(","));
        return "{" + jsonString + "}";
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
            .key();
        return value.isEmpty() ? field.getName() : value;
    }
}
