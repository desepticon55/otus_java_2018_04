package com.otus.homework2;

import java.lang.instrument.Instrumentation;

public class ObjectCalculator {

  private static Instrumentation instrumentation;

  public static void premain(String args, Instrumentation instrumentation) {
    ObjectCalculator.instrumentation = instrumentation;
  }

  public static long getObjectSize(Object o) {
    return instrumentation.getObjectSize(o);
  }
}
