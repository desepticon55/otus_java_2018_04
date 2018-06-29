package com.otus.homework5.framework;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

@Log4j2
public class TestRunner {

  public static void run(Class<?> c) {
    Objects.requireNonNull(c, "Class should not be null");
    Object o = ReflectionHelper.createObject(c);
    runClassMethods(o, Before.class);
    runClassMethods(o, Test.class);
    runClassMethods(o, After.class);
  }

  public static void run(String packageName) {
    Objects.requireNonNull(packageName, "Package name should not be null");
    if (packageName.isEmpty()) {
      throw new IllegalArgumentException("Package name should not be empty");
    }
    List<Class<?>> classes = ReflectionHelper.getClassesWithPackage(packageName);
    classes.stream()
            .filter(cl -> !cl.isInterface() || !cl.isAnnotation())
            .map(ReflectionHelper::createObject)
            .forEach(o -> {
              runClassMethods(o, Before.class);
              runClassMethods(o, Test.class);
              runClassMethods(o, After.class);
            });
  }

  private static void runClassMethods(Object o, Class<? extends Annotation> annotationClass) {
    Method[] methods = o.getClass().getDeclaredMethods();
    Stream.of(methods).parallel()
            .filter(method -> method.isAnnotationPresent(annotationClass))
            .forEach(method -> runMethod(o, method));
  }

  @SneakyThrows
  private static void runMethod(Object o, Method method) {
    if (method.isAnnotationPresent(Test.class)) {
      long startTime = System.currentTimeMillis();
      log.info("START TEST {}#{}", o.getClass().getCanonicalName(), method.getName());
      method.invoke(o);
      log.info("TIME TEST {}#{}: {} ms.", o.getClass().getCanonicalName(), method.getName(), System.currentTimeMillis() - startTime);
    } else {
      method.invoke(o);
    }
  }
}
