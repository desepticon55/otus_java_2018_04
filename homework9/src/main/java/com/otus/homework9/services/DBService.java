package com.otus.homework9.services;

import com.otus.homework9.entity.DataSet;

import java.util.List;

public interface DBService {
  <T extends DataSet> void save(T entity);

  <T extends DataSet> T load(Long id, Class<T> clazz);

  <T extends DataSet> List<T> load(Class<T> clazz);

  void printHello();
}
