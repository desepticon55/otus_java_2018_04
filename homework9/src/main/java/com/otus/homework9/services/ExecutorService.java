package com.otus.homework9.services;

import com.otus.homework9.dao.DAO;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InjectByType;
import com.otus.homework9.entity.DataSet;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Component
@NoArgsConstructor
public class ExecutorService implements Executor {

  @InjectByType
  private DBService dbService;

  @Override
  @SneakyThrows
  public <T extends DataSet> void save(T entity) {
    dbService.save(entity);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> T load(Long id, Class<T> clazz) {
    return dbService.load(id, clazz);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> List<T> load(Class<T> clazz) {
    return dbService.load(clazz);
  }
}
