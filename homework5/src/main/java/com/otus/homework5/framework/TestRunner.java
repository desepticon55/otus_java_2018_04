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
    runClassMethods(c, Before.class);
    runClassMethods(c, Test.class);
    runClassMethods(c, After.class);
  }

  public static void run(String packageName) {
    Objects.requireNonNull(packageName, "Package name should not be null");
    if (packageName.isEmpty()) {
      throw new IllegalArgumentException("Package name should not be empty");
    }
    List<Class<?>> classes = getClassesWithPackage(packageName);
    classes.stream()
            .filter(cl -> !cl.isInterface() || !cl.isAnnotation())
            .forEach(cl -> {
              runClassMethods(cl, Before.class);
              runClassMethods(cl, Test.class);
              runClassMethods(cl, After.class);
            });
  }

  @SneakyThrows
  private static void runClassMethods(Class<?> c, Class<? extends Annotation> annotationClass) {
    Object o = c.newInstance();
    Objects.requireNonNull(o);
    Method[] methods = c.getDeclaredMethods();
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

  @SneakyThrows
  private static List<Class<?>> getClassesWithPackage(String packageName) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Objects.requireNonNull(classLoader);

    String path = packageName.replace('.', '/');
    Enumeration resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<>();
    while (resources.hasMoreElements()) {
      URL resource = (URL) resources.nextElement();
      dirs.add(new File(resource.getFile()));
    }
    List<Class<?>> classes = new ArrayList<>();
    for (File directory : dirs) {
      classes.addAll(findClasses(directory, packageName));
    }
    return classes;
  }

  private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    if (!directory.exists()) {
      return classes;
    }
    File[] files = directory.listFiles();
    for (File file : Objects.requireNonNull(files)) {
      if (file.isDirectory()) {
        assert !file.getName().contains(".");
        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
      }
    }
    return classes;
  }
}
