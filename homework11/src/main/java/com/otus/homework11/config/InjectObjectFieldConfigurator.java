package com.otus.homework11.config;

import com.otus.homework11.ObjectFactory;
import com.otus.homework11.annotations.InjectByType;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

/**
 * Класс инжектит созданные бины в поля, помеченные аннотацией @InjectByType
 */
public class InjectObjectFieldConfigurator implements ObjectConfigurator {

  @Override
  @SuppressWarnings("unchecked")
  public <T> void configure(T t) {
    Class<?> c = t.getClass();
    Set<Field> fields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    fields.stream()
            .filter(f -> f.isAnnotationPresent(InjectByType.class))
            .forEach(f -> {
              try {
                Object object = ObjectFactory.getInstance().createObject(f.getType());
                f.setAccessible(true);
                f.set(t, object);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
  }
}
