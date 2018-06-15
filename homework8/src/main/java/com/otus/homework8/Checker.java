package com.otus.homework8;

import com.google.common.collect.Maps;

import java.util.Map;

class Checker {
  static boolean isIterable(Object object) {
    return object instanceof Iterable;
  }

  static boolean isPrimitive(Object object) {
    return object == null ||
            object instanceof Number ||
            object instanceof Boolean ||
            object instanceof Character ||
            object instanceof String;
  }

  static boolean isArray(Object object) {
    return object.getClass().isArray();
  }

  static boolean isMap(Object object) {
    return object instanceof Map;
  }
}
