package com.otus.homework9;

import com.otus.homework9.annotations.Entity;
import com.otus.homework9.config.LocalH2DataSource;
import com.otus.homework9.entity.DataSet;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.otus.homework9.config.Configurator.configurator;

public class H2DAO {

  private DataSource dataSource = configurator.getConfiguredObject(LocalH2DataSource.class);

  @SuppressWarnings("unchecked")
  public <T> void insertEntity(T entity) throws Exception {
    Objects.requireNonNull(entity, "Entity should be not null");
    Entity entityAnnotation = entity.getClass().getAnnotation(Entity.class);
    Objects.requireNonNull(entityAnnotation, "Entity name should be not null");
    Set<Field> fields = ReflectionUtils.getAllFields(entity.getClass(), Objects::nonNull);
    if (fields.isEmpty()) {
      throw new EmptyEntityException();
    }
    String tableName = entityAnnotation.name().toLowerCase();
    String parameters = fields.stream().map(el -> "?").collect(Collectors.joining(","));
    String fieldNames = fields.stream().map(Field::getName).collect(Collectors.joining(","));
    String sql = String.format("insert into %s (%s) values (%s)", tableName, fieldNames, parameters);
    try(Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      List<Object> values = getFieldValuesFromObject(fields, entity);
      for (int i = 1; i <= values.size(); i++) {
        preparedStatement.setObject(i, values.get(i - 1));
      }
      preparedStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private <T> List<Object> getFieldValuesFromObject(Set<Field> fields, T entity) {
    return fields.stream()
            .peek(el -> el.setAccessible(true))
            .map(el -> {
              try {
                return el.get(entity);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
              }
            })
            .collect(Collectors.toList());
  }

  public <T extends DataSet> T selectEntityById(Long id, Class<?> clazz) throws SQLException {
    Objects.requireNonNull(id, "Id should be not null");
    String entityName = getEntityName(clazz);
    String fieldNames = getEntityFieldsStr(clazz);
    String sql = String.format("select %s from %s where id = %d", fieldNames, entityName, id);
    try(Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      return resultSetToObject(rs, clazz);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public <T extends DataSet> List<T> selectListEntities(Class<?> clazz) throws SQLException {
    String entityName = getEntityName(clazz);
    String fieldNames = getEntityFieldsStr(clazz);
    String sql = String.format("select %s from %s", fieldNames, entityName);
    try(Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      return resultSetToList(rs, clazz);
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("unchecked")
  private String getEntityFieldsStr(Class<?> clazz) {
    Objects.requireNonNull(clazz, "Class entity should be not null");
    Set<Field> fields = ReflectionUtils.getAllFields(clazz, Objects::nonNull);
    return fields.stream()
            .map(Field::getName)
            .map(String::toLowerCase)
            .collect(Collectors.joining(","));
  }

  private String getEntityName(Class<?> clazz) {
    Objects.requireNonNull(clazz, "Class entity should be not null");
    Entity entityAnnotation = clazz.getAnnotation(Entity.class);
    Objects.requireNonNull(entityAnnotation, "Entity name should be not null");
    return entityAnnotation.name();
  }

  @SneakyThrows
  @SuppressWarnings("unchecked")
  private <T> List<T> resultSetToList(ResultSet rs, Class<?> c) {
    List<T> result = new ArrayList<>();
    Set<Field> fields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    while (rs.next()) {
      T o = (T) c.newInstance();
      fields.forEach(f -> {
        f.setAccessible(true);
        rowMapper(o, f, rs);
      });
      result.add(o);
    }
    return result;
  }

  @SneakyThrows
  @SuppressWarnings("unchecked")
  private <T> T resultSetToObject(ResultSet rs, Class<?> c) {
    Set<Field> fields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    rs.next();
    T o = (T) c.newInstance();
    fields.forEach(f -> {
      f.setAccessible(true);
      rowMapper(o, f, rs);
    });
    return o;
  }

  private void rowMapper(Object o, Field f, ResultSet rs) {
    try {
      if (f.getType().equals(Integer.class)) {
        f.set(o, rs.getInt(f.getName().toLowerCase()));
      }
      if (f.getType().equals(Float.class)) {
        f.set(o, rs.getFloat(f.getName().toLowerCase()));
      }
      if (f.getType().equals(Long.class)) {
        f.set(o, rs.getLong(f.getName().toLowerCase()));
      }
      if (f.getType().equals(String.class)) {
        f.set(o, rs.getString(f.getName().toLowerCase()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
