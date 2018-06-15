package com.otus.homework8;

import org.json.simple.JSONAware;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static com.otus.homework8.Symbols.*;

public class JsonObject extends HashMap<Object, Object> implements JSONAware {

  @Override
  public String toJSONString() {
    return toJsonString(this);
  }

  public static String toJsonString(Map map) {
    if (Objects.isNull(map)) {
      return NULL.toString();
    }

    StringBuilder sb = new StringBuilder();
    boolean first = true;
    sb.append(BRACE);
    for (Object o : map.entrySet()) {
      if (first) {
        first = false;
      } else {
        sb.append(COMMA);
      }
      Entry entry = (Entry) o;
      toJsonString(String.valueOf(entry.getKey()), entry.getValue(), sb);
    }
    sb.append(CLOSING_BRACE);
    return sb.toString();
  }

  private static void toJsonString(String key, Object value, StringBuilder sb) {
    sb.append(DOUBLE_APOSTROPHE);
    if (key == null) {
      sb.append(NULL);
    } else {
      JsonValue.escape(key, sb);
    }

    sb.append(DOUBLE_APOSTROPHE).append(BINARY);
    sb.append(JsonValue.toJsonString(value));
  }
}
