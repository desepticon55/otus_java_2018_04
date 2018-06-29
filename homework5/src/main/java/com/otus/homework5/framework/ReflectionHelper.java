package com.otus.homework5.framework;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class ReflectionHelper {

  @SneakyThrows
  public static Object createObject(Class<?> c) {
    Object o = c.newInstance();
    Objects.requireNonNull(o);
    return o;
  }

  @SneakyThrows
  public static List<Class<?>> getClassesWithPackage(String packageName) {
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
