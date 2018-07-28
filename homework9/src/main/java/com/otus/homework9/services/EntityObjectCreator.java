package com.otus.homework9.services;

import com.otus.homework9.annotations.*;
import com.otus.homework9.datasources.LocalH2DataSource;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class EntityObjectCreator {

  @InjectByType(type = LocalH2DataSource.class)
  private DataSource dataSource;

  @SneakyThrows
  @SuppressWarnings("unchecked")
  public <T> void createEntity(Class<?> c) {
    if (!c.isAnnotationPresent(Entity.class)) {
      return;
    }
    Set<Field> declaredFields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    List<Field> fields = declaredFields.stream()
            .filter(el -> el.isAnnotationPresent(Column.class) || el.isAnnotationPresent(OneToOne.class))
            .collect(Collectors.toList());
    Connection connection = dataSource.getConnection();
    Statement st = connection.createStatement();
    StringBuilder sb = new StringBuilder();
    Entity entity = c.getAnnotation(Entity.class);
    sb.append(String.format("create table %s (", entity.name()));
    fields.forEach(el -> {
      if (el.isAnnotationPresent(Id.class)) {
        sb.append(String.format("%s int(20) identity", el.getName()));
      } else if (el.isAnnotationPresent(OneToOne.class)) {
        sb.append(String.format("%s_id int(20),", el.getName()));
      } else {
        sb.append(String.format("%s %s,", el.getName(), getDBTypeByClass(el.getType())));
      }
    });
    sb.append(")");
    st.execute(sb.toString());
    addForeignKey(entity.name(), declaredFields, st);
  }

  private void addForeignKey(String tableName, Set<Field> declaredFields, Statement st) {
    List<Field> fields = declaredFields.stream()
            .filter(el -> el.isAnnotationPresent(OneToOne.class))
            .collect(Collectors.toList());
    fields.forEach(field -> {
      String fieldName = field.getName();
      String foreignKeyIdentifier = UUID.randomUUID().toString().replaceAll("-", "");
      String sql = String.format("alter table %s add CONSTRAINT FK%s FOREIGN KEY (%s_id) references %s(id)", tableName,
              foreignKeyIdentifier, fieldName, fieldName);
      try {
        st.execute(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
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
