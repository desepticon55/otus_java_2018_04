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

/**
 * Фабрика, позволяющая создавать, настраивать бины, сущности БД и т.д.
 */
public class ObjectFactory {
  private final static ObjectFactory INSTANCE = new ObjectFactory();
  private Map<String, Object> configuredObjects = new ConcurrentHashMap<>();
  private Reflections scanner;

  private List<ObjectConfigurator> configurators = new ArrayList<>();

  private static boolean createdSingletonsAndEntities = false;

  public static ObjectFactory getInstance() {
    return INSTANCE;
  }

  private ObjectFactory() {
  }

  /**
   * Метод, инициализирующий фабрику. В параметр передается main класс.
   * Создаются, настраиваются и помещаются в мапу бины, аннотированные @Component которые в дальнейшем будут использоваться для инжекта
   * Создаются таблицы в БД, которые описывают классы аннотированные @Entity
   * @param classRunner main класс
   */
  public void init(Class<?> classRunner) {
    scanner = new Reflections(getClass().getPackage().getName(), classRunner.getPackage().getName());
    findConfigurations();
    if (!createdSingletonsAndEntities) {
      Set<Class<?>> componentClasses = scanner.getTypesAnnotatedWith(Component.class);
      componentClasses.forEach(c -> configuredObjects.put(c.getCanonicalName(), createObject(c)));
      EntityObjectCreator entityObjectCreator = createObject(EntityObjectCreator.class);
      List<Class<?>> classesWithPackage = ReflectionHelper.getClassesWithPackage(classRunner.getPackage().getName());
      classesWithPackage.forEach(entityObjectCreator::createEntity);
      createdSingletonsAndEntities = true;
    }
  }

  /**
   * Вспомогательный метод для поиска классов, описывающих конфигурацию бинов
   */
  private void findConfigurations() {
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

  /**
   * Метод, создающий объект по классу
   * @param type класс, объект которого создать
   * @return созданный и настроенный объект
   */
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

  /**
   * Метод, позволяющий выполнить init-метод бина (двухфакторный конструктор)
   * @param type класс, в котором описан необходимый init-метод
   * @param t объект, у которого требуется выполнить init-метод
   */
  @SuppressWarnings("unchecked")
  private <T> void invokeInitMethods(Class<?> type, T t) {
    Set<Method> methods = ReflectionUtils.getAllMethods(type);
    for (Method method : methods) {
      if (method.isAnnotationPresent(InitMethod.class)) {
        try {
          method.invoke(t);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Вспомогательный метод для конфигурирования бинов
   * @param t объект, который необходимо сконфигурировать
   */
  private <T> void configure(T t) {
    for (ObjectConfigurator configurator : configurators) {
      configurator.configure(t);
    }
  }

  /**
   * Вспомогательный метод, который ищет классы, имплементирующие интерфейс(если инжект происходит по интерфейсу)
   * Если находит более одного класса, реализущего интерфейс -> бросаем исключение
   * @param type исследуемый класс
   * @return результирующий класс
   */
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
