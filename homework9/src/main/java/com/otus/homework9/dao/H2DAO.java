package com.otus.homework9.dao;

import com.otus.homework9.annotations.*;
import com.otus.homework9.datasources.LocalH2DataSource;
import com.otus.homework9.entity.Address;
import com.otus.homework9.entity.EmptyEntityException;
import com.otus.homework9.entity.DataSet;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Обычный DAO
 */
@Component
@NoArgsConstructor
public class H2DAO implements DAO {

  @InjectByType(type = LocalH2DataSource.class)
  private DataSource dataSource;

  /**
   * Метод вставляет объект в таблицу
   * @param entity объект, который необходимо вставить в таблицу
   * @param fields поля данного объекта
   * @param sql sql запрос
   */
  @SuppressWarnings("unchecked")
  public <T> void insertEntity(T entity, Set<Field> fields, String sql) {
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

  /**
   * Вспомогательный метод для получения списка значений полей объекта
   * @param fields поля объекта
   * @param entity объект
   * @return список значений объекта
   */
  @SuppressWarnings("unchecked")
  private <T> List<Object> getFieldValuesFromObject(Set<Field> fields, T entity) {
    return fields.stream()
            .peek(el -> el.setAccessible(true))
            .map(el -> {
              try {
                if (Objects.nonNull(el.get(entity)) && el.isAnnotationPresent(OneToOne.class)) {
                  Field idField = ReflectionUtils.getAllFields(el.getType(), Objects::nonNull).stream()
                          .filter(field -> field.isAnnotationPresent(Id.class))
                          .findFirst()
                          .orElseThrow(() -> new RuntimeException("Не найден идентификатор у связанной сущности"));
                  idField.setAccessible(true);
                  System.out.println(idField.get(el.get(entity)));
                  return idField.get(el.get(entity));
                } else {
                  return el.get(entity);
                }
              } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
              }
            })
            .collect(Collectors.toList());
  }

  /**
   * Метод получает объект по идентификатору
   * @param id идентификатор объекта
   * @param clazz класс, описывающий нужный объект
   * @param sql запрос, по которому получаем объект
   * @return объект
   */
  public <T extends DataSet> T selectEntityById(Long id, Class<?> clazz, String sql) {
    try(Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      return resultSetToObject(rs, clazz);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Метод получает список объектов
   * @param clazz класс, описывающий нужный объект
   * @param sql запрос, по которому получаем объект
   * @return список объектов
   */
  public <T extends DataSet> List<T> selectListEntities(Class<?> clazz, String sql) {
    try(Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      return resultSetToList(rs, clazz);
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  /**
   * Вспомогательный метод для конвертации ResultSet в список объектов нужного класса
   * @param rs ResultSet
   * @param c класс, описывающий нужный объект
   * @return список объектов
   */
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

  /**
   * Вспомогательный метод для конвертации ResultSet в объект нужного класса
   * @param rs ResultSet
   * @param c класс, описывающий нужный объект
   * @return объект
   */
  @SneakyThrows
  @SuppressWarnings("unchecked")
  private <T> T resultSetToObject(ResultSet rs, Class<?> c) {
    if (!rs.next()) {
      return null;
    }
    Set<Field> fields = ReflectionUtils.getAllFields(c, Objects::nonNull);
    T o = (T) c.newInstance();
    fields.forEach(f -> {
      f.setAccessible(true);
      rowMapper(o, f, rs);
    });
    return o;
  }

  //todo временный костыль

  /**
   * Вспомогательный метод для маппинга значений из БД в поля объекта
   * @param o объект, поля которого надо заполнить
   * @param f поле
   * @param rs ResultSet
   */
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
      if (f.isAnnotationPresent(OneToOne.class)) {
        long id = rs.getLong(String.format("%s_id", f.getName()));
        DataSet entity = selectEntityById(id, f.getType(), String.format("select * from %s where id = %d", f.getName().toLowerCase(), id));
        f.set(o, entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
