package com.otus.homework9.config;

import com.otus.homework5.framework.ReflectionHelper;
import com.otus.homework9.Application;
import com.otus.homework9.Symbols;
import com.otus.homework9.annotations.*;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.otus.homework9.Symbols.CIRCLE_LEFT_BRACKET;
import static com.otus.homework9.Symbols.CIRCLE_RIGHT_BRACKET;
import static com.otus.homework9.Symbols.COMMA;

public class Configurator {

  public static Configurator configurator = new Configurator();
  private Map<String, Object> configuredObjects = new ConcurrentHashMap<>();

  private boolean isConfigure = false;

  private Configurator() {
    configure();
  }

  @SuppressWarnings("unchecked")
  public <T> T getConfiguredObject(Class<?> c) {
    return (T) configuredObjects.get(c.getCanonicalName());
  }

  private void configure() {
    if (isConfigure) {
      throw new ConfigurationException("Configuration was performed earlier");
    }

    List<Class<?>> classesWithPackage = ReflectionHelper.getClassesWithPackage(Application.class.getPackage().getName());
    classesWithPackage.forEach(el -> {
      injectPropertyInField(el);
      createTablesByEntity(el);
    });
    isConfigure = true;
  }

  @SneakyThrows
  @SuppressWarnings("unchecked")
  private void createTablesByEntity(Class<?> c) {
    if (!c.isAnnotationPresent(Entity.class)) {
      return;
    }
    Set<Field> declaredFields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    List<Field> fields = declaredFields.stream()
            .filter(el -> el.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());
    DataSource dataSource = getConfiguredObject(LocalH2DataSource.class);
    Connection connection = dataSource.getConnection();
    Statement st = connection.createStatement();
    StringBuilder sb = new StringBuilder();
    Entity entity = c.getAnnotation(Entity.class);
    sb.append(String.format("create table %s " + CIRCLE_LEFT_BRACKET, entity.name()));
    fields.forEach(el -> {
      if (el.isAnnotationPresent(Id.class)) {
        sb.append(String.format("%s int(20) identity", el.getName()));
      } else {
        sb.append(String.format("%s %s" + COMMA, el.getName(), getDBTypeByClass(el.getType())));
      }
    });
    sb.append(CIRCLE_RIGHT_BRACKET);
    st.execute(sb.toString());
  }

  @SneakyThrows
  private void injectPropertyInField(Class<?> c) {
    if (!c.isAnnotationPresent(Configuration.class)) {
      return;
    }
    Object object = ReflectionHelper.createObject(c);
    Properties properties = new Properties();
    properties.load(getClass().getResourceAsStream("/application.properties"));
    Field[] declaredFields = c.getDeclaredFields();
    Stream.of(declaredFields)
            .filter(el -> el.isAnnotationPresent(Value.class))
            .forEach(el -> {
              Value annotation = el.getAnnotation(Value.class);
              String propertyKey = annotation.value();
              String propertyValue = properties.getProperty(propertyKey);
              el.setAccessible(true);
              try {
                el.set(object, propertyValue);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    configuredObjects.put(c.getCanonicalName(), object);
  }

  private String getDBTypeByClass(Class<?> c) {
    if (c.equals(Integer.class)) {
      return "int";
    } else  if (c.equals(String.class)) {
      return "varchar(255)";
    }
    if (c.equals(Double.class)) {
      return "decimal(18,2)";
    }
    return "";
  }
}
