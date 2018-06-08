package com.otus.homework8;

import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import static com.otus.homework8.Checker.*;

public class JsonObjectWriter {

  public String toJson(Object object) {
    Object o = createObject(object);
    return ((JsonObject) o).toJSONString();
  }

  private Object createObject(Object object) {
    if (isPrimitive(object)) {
      return object;
    }
    if (isArray(object)) {
      return createArray(object);
    }
    if (isIterable(object)) {
      return createArrayFromIterable((Iterable) object);
    }
    if (isMap(object)) {
      return createMap((Map) object);
    }
    return createJsonObject(object);
  }

  @SuppressWarnings("unchecked")
  private Object createArrayFromIterable(Iterable object) {
    JsonArray array = new JsonArray();
    object.forEach(el -> array.add(createObject(el)));
    return array;
  }

  @SuppressWarnings("unchecked")
  private Object createArray(Object object) {
    JsonArray array = new JsonArray();
    for (int i = 0; i < Array.getLength(object); i++) {
      array.add(createObject(Array.get(object, i)));
    }
    return array;
  }

  @SuppressWarnings("unchecked")
  private Object createMap(Map map) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.putAll(map);
    return jsonObject;
  }

  @SuppressWarnings("unchecked")
  private <T> JsonObject createJsonObject(T o) {
    JsonObject jsonObject = new JsonObject();
    Field[] fields = o.getClass().getDeclaredFields();
    Arrays.asList(fields).forEach(f -> {
      try {
        f.setAccessible(true);
        jsonObject.put(f.getName(), createObject(f.get(o)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return jsonObject;
  }
}
