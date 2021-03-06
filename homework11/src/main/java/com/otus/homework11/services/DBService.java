package com.otus.homework11.services;

import com.otus.homework11.entity.DataSet;

import java.util.List;

public interface DBService {
  <T extends DataSet> void save(T entity);

  <T extends DataSet> T findById(Long id, Class<T> clazz);

  <T extends DataSet> List<T> findAll(Class<T> clazz);
}
