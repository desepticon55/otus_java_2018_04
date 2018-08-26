package com.otus.homework12.config;

import com.otus.homework12.annotations.Configuration;
import com.otus.homework12.annotations.Value;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Класс инжектит проперти в поля, помеченные аннотацией @Value
 */
public class PropertyFiledConfigurator implements ObjectConfigurator {

  @Override
  @SneakyThrows
  public <T> void configure(T t) {
    Class<?> c = t.getClass();
    if (c.isAnnotationPresent(Configuration.class)) {
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
                  el.set(t, propertyValue);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              });
    }
  }
}
