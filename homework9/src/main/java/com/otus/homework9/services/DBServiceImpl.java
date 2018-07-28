package com.otus.homework9.services;

import com.otus.homework9.annotations.Entity;
import com.otus.homework9.annotations.InjectByType;
import com.otus.homework9.annotations.OneToOne;
import com.otus.homework9.dao.DAO;
import com.otus.homework9.entity.DataSet;
import com.otus.homework9.entity.EmptyEntityException;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DBServiceImpl implements DBService {

  @InjectByType
  private DAO dao;

  @Override
  @SneakyThrows
  @SuppressWarnings("unchecked")
  public <T extends DataSet> void save(T entity) {
    Objects.requireNonNull(entity, "Entity should be not null");
    Entity entityAnnotation = entity.getClass().getAnnotation(Entity.class);
    Objects.requireNonNull(entityAnnotation, "Entity name should be not null");
    Set<Field> fields = ReflectionUtils.getAllFields(entity.getClass(), Objects::nonNull);
    if (fields.isEmpty()) {
      throw new EmptyEntityException();
    }
    String tableName = entityAnnotation.name().toLowerCase();
    String parameters = fields.stream().map(el -> "?").collect(Collectors.joining(","));
    String fieldNames = fields.stream()
            .map(el -> {
              if (el.isAnnotationPresent(OneToOne.class)) {
                return String.format("%s_id", el.getName());
              }
              return el.getName();
            })
            .collect(Collectors.joining(","));
    String sql = String.format("insert into %s (%s) values (%s)", tableName, fieldNames, parameters);

    dao.insertEntity(entity, fields, sql);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> T load(Long id, Class<T> clazz) {
    Objects.requireNonNull(id, "Id should be not null");
    String entityName = getEntityName(clazz);
    String fieldNames = getEntityFieldsStr(clazz);
    String sql = String.format("select %s from %s where id = %d", fieldNames, entityName, id);
    return dao.selectEntityById(id, clazz, sql);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> List<T> load(Class<T> clazz) {
    String entityName = getEntityName(clazz);
    String fieldNames = getEntityFieldsStr(clazz);
    String sql = String.format("select %s from %s", fieldNames, entityName);
    return dao.selectListEntities(clazz, sql);
  }

  @Override
  public void printHello() {

  }

  @SuppressWarnings("unchecked")
  private String getEntityFieldsStr(Class<?> clazz) {
    Objects.requireNonNull(clazz, "Class entity should be not null");
    Set<Field> fields = ReflectionUtils.getAllFields(clazz, Objects::nonNull);
    return fields.stream()
            .map(el -> {
              if (el.isAnnotationPresent(OneToOne.class)) {
                return String.format("%s_id", el.getName());
              }
              return el.getName();
            })
            .map(String::toLowerCase)
            .collect(Collectors.joining(","));
  }

  private String getEntityName(Class<?> clazz) {
    Objects.requireNonNull(clazz, "Class entity should be not null");
    Entity entityAnnotation = clazz.getAnnotation(Entity.class);
    Objects.requireNonNull(entityAnnotation, "Entity name should be not null");
    return entityAnnotation.name();
  }
}
