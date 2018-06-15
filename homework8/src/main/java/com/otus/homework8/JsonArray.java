package com.otus.homework8;

import org.json.simple.JSONAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.otus.homework8.Symbols.*;

public class JsonArray extends ArrayList implements JSONAware {

  @Override
  public String toJSONString() {
    return toJsonString(this);
  }

  public static String toJsonString(List list) {
    if (Objects.isNull(list)) {
      return NULL.toString();
    }

    StringBuilder sb = new StringBuilder();
    boolean first = true;
    sb.append(SQUARE_BRACKET);
    for (Object o : list) {
      if (first) {
        first = false;
      } else {
        sb.append(COMMA);
      }
      if (Objects.isNull(o)) {
        sb.append(NULL);
      } else {
        sb.append(JsonValue.toJsonString(o));
      }
    }
    sb.append(CLOSING_SQUARE_BRACKET);
    return sb.toString();
  }

}
