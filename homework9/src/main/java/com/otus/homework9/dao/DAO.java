package com.otus.homework9.dao;

import com.otus.homework9.entity.DataSet;

import java.util.List;

public interface DAO {

  <T> void insertEntity(T entity);

  <T extends DataSet> T selectEntityById(Long id, Class<?> clazz);

  public <T extends DataSet> List<T> selectListEntities(Class<?> clazz);
}
