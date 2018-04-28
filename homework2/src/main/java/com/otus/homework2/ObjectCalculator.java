package com.otus.homework2;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectCalculator {

  private static Instrumentation instrumentation;

  public static void premain(String args, Instrumentation instrumentation) {
    ObjectCalculator.instrumentation = instrumentation;
  }

  public static long getObjectSize(Object o) {
    return calculateObjectSize(o);
  }

  private static Long calculateObjectSize(Object o) {
    Field[] fields = o.getClass().getDeclaredFields();

    if (fields.length == 0) {
      return instrumentation.getObjectSize(o);
    }
    String canonicalName = o.getClass().getCanonicalName();

    if (canonicalName.equals(Integer.class.getCanonicalName()) ||
            canonicalName.equals(Float.class.getCanonicalName())) {
      return 4L;
    }

    if (canonicalName.equals(Double.class.getCanonicalName()) ||
            canonicalName.equals(Long.class.getCanonicalName())) {
      return 8L;
    }

    return Stream.of(fields)
            .peek(System.out::println)
            .map(f -> {
              try {
                f.setAccessible(true);
                return ObjectCalculator.calculateObjectSize(f.get(o));
              } catch (IllegalAccessException e) {
                e.printStackTrace();
                return 0L;
              }
            })
            .collect(Collectors.summarizingLong(f -> (long) f))
            .getSum();
  }
}
