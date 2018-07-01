package com.otus.homework9;

import com.otus.homework5.framework.ReflectionHelper;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InitMethod;
import com.otus.homework9.config.ObjectConfigurator;
import com.otus.homework9.services.EntityObjectCreator;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectFactory {
  private final static ObjectFactory ourInstance = new ObjectFactory();
  private Map<String, Object> configuredObjects = new ConcurrentHashMap<>();
  private Reflections scanner = new Reflections(Application.class.getPackage().getName());

  private List<ObjectConfigurator> configurators = new ArrayList<>();

  private static boolean createdSingletonsAndEntities = false;

  public static ObjectFactory getInstance() {
    return ourInstance;
  }

  private ObjectFactory() {
    Set<Class<? extends ObjectConfigurator>> classes = scanner.getSubTypesOf(ObjectConfigurator.class);
    classes.forEach(c -> {
      if (!Modifier.isAbstract(c.getModifiers())) {
        try {
          configurators.add(c.newInstance());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void init() {
    if (!createdSingletonsAndEntities) {
      Set<Class<?>> componentClasses = scanner.getTypesAnnotatedWith(Component.class);
      componentClasses.forEach(c -> configuredObjects.put(c.getCanonicalName(), createObject(c)));
      EntityObjectCreator entityObjectCreator = createObject(EntityObjectCreator.class);
      List<Class<?>> classesWithPackage = ReflectionHelper.getClassesWithPackage(Application.class.getPackage().getName());
      classesWithPackage.forEach(entityObjectCreator::createEntity);
      createdSingletonsAndEntities = true;
    }
  }

  @SneakyThrows
  @SuppressWarnings("unchecked")
  public <T> T createObject(Class<?> type) {
    T o = (T) configuredObjects.get(type.getCanonicalName());
    if (Objects.isNull(o)) {
      type = resolveImpl(type);
      T t = (T) type.newInstance();
      configure(t);
      invokeInitMethods(type, t);
      return t;
    }
    return o;
  }

  @SuppressWarnings("unchecked")
  private <T> void invokeInitMethods(Class<?> type, T t) throws IllegalAccessException, InvocationTargetException {
    Set<Method> methods = ReflectionUtils.getAllMethods(type);
    for (Method method : methods) {
      if (method.isAnnotationPresent(InitMethod.class)) {
        method.invoke(t);
      }
    }
  }

  private <T> void configure(T t) {
    for (ObjectConfigurator configurator : configurators) {
      configurator.configure(t);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> Class<T> resolveImpl(Class<T> type) {
    if (type.isInterface()) {
      Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
      if (classes.size() != 1) {
        throw new RuntimeException("0 or more than one impl found for " + type + " update your config");
      }
      type = (Class<T>) classes.iterator().next();
    }
    return type;
  }
}