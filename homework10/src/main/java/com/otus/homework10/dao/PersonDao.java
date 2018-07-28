package com.otus.homework10.dao;

import com.otus.homework10.DBServiceHibernateImpl;
import com.otus.homework10.entity.Person;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InjectByType;

import java.util.List;

@Component
public class PersonDao {

  @InjectByType
  private DBServiceHibernateImpl dbService;

  public List<Person> findAll() {
    return dbService.findAll(Person.class);
  }

  public Boolean save(Person person) {
    try {
      dbService.save(person);
      return Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      return Boolean.FALSE;
    }
  }

  public Person findById(Long id) {
    return dbService.findById(id, Person.class);
  }
}
