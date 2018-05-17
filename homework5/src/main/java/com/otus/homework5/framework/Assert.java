package com.otus.homework5.framework;

import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
public class Assert {

  public static void assertTrue(boolean condition) {
      assertTrue(condition, "Expected TRUE, actual FALSE");
  }

  public static void assertTrue(boolean condition, String message) {
    if (!condition) {
      fail(message);
    }
  }

  public static void assertFalse(boolean condition) {
    assertFalse(condition, "Expected FALSE, actual TRUE");
  }

  public static void assertFalse(boolean condition, String message) {
    if (condition) {
      fail(message);
    }
  }

  public static void assertEquals(Object expected, Object actual, String message) {
    if (Objects.isNull(expected) && Objects.isNull(actual)) {
      return;
    }
    if (!expected.equals(actual)) {
      fail(message);
    }
  }

  public static void assertEquals(Object expected, Object actual) {
    assertEquals(expected, actual, "Fail, objects not equals");
  }

  public static void fail(String message) {
    if (message == null) {
      throw new AssertionError();
    }
    throw new AssertionError(message);
  }

}
