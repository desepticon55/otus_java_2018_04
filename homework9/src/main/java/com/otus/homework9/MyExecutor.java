package com.otus.homework9;

import com.otus.homework9.config.LocalH2DataSource;
import com.otus.homework9.entity.DataSet;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.otus.homework9.config.Configurator.configurator;

public class MyExecutor implements Executor {

  private H2DAO h2DAO;

  public MyExecutor() {
    this.h2DAO = new H2DAO();
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> void save(T entity) {
    h2DAO.insertEntity(entity);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> T load(long id, Class<T> clazz) {
    return h2DAO.selectEntityById(id, clazz);
  }

  @Override
  @SneakyThrows
  public <T extends DataSet> List<T> load(Class<T> clazz) {
    return h2DAO.selectListEntities(clazz);
  }
}
