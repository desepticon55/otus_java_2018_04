package com.otus.homework11.dao;

import com.otus.homework11.entity.DataSet;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public interface DAO {

  <T> void insertEntity(T entity, Set<Field> fields, String sql);

  <T extends DataSet> T selectEntityById(Long id, Class<?> clazz, String sql);

  public <T extends DataSet> List<T> selectListEntities(Class<?> clazz, String sql);
}
