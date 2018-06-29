package com.otus.homework9;

import com.otus.homework9.entity.DataSet;

import java.util.List;

public interface Executor {
  <T extends DataSet> void save(T entity);

  <T extends DataSet> T load(long id, Class<T> clazz);

  <T extends DataSet> List<T> load(Class<T> clazz);
}
