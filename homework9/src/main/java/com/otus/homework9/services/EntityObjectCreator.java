package com.otus.homework9.services;

import com.otus.homework9.annotations.Column;
import com.otus.homework9.annotations.Entity;
import com.otus.homework9.annotations.Id;
import com.otus.homework9.annotations.InjectByType;
import com.otus.homework9.datasources.LocalH2DataSource;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityObjectCreator {

  @InjectByType(type = LocalH2DataSource.class)
  private DataSource dataSource;

  @SneakyThrows
  public <T> void createEntity(Class<?> c) {
    if (!c.isAnnotationPresent(Entity.class)) {
      return;
    }
    Set<Field> declaredFields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    List<Field> fields = declaredFields.stream()
            .filter(el -> el.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());
    Connection connection = dataSource.getConnection();
    Statement st = connection.createStatement();
    StringBuilder sb = new StringBuilder();
    Entity entity = c.getAnnotation(Entity.class);
    sb.append(String.format("create table %s (", entity.name()));
    fields.forEach(el -> {
      if (el.isAnnotationPresent(Id.class)) {
        sb.append(String.format("%s int(20) identity", el.getName()));
      } else {
        sb.append(String.format("%s %s,", el.getName(), getDBTypeByClass(el.getType())));
      }
    });
    sb.append(")");
    st.execute(sb.toString());
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
