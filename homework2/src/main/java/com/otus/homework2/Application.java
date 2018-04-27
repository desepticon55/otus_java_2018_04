package com.otus.homework2;

import com.google.common.collect.Lists;

/**
 * Запускать командой
 * java -javaagent:homework2-jar-with-dependencies.jar com.otus.homework2.Application
 */
public class Application {

  public static void main(String[] args) {
    printObjectSize(new Object());
    printObjectSize(new Employee("Иванов", "Иван", 24));
    printObjectSize("");
    printObjectSize(new int[]{5, 4, 1});
    printObjectSize(Lists.newArrayList(5, 4, 1));
  }

  public static void printObjectSize(Object obj) {
    System.out.println(String.format("%s, size=%s", obj.getClass()
            .getCanonicalName(), ObjectCalculator.getObjectSize(obj)));
  }
}
