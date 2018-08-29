package com.otus.homework12.dao;

import com.otus.homework12.DBServiceHibernateImpl;
import com.otus.homework12.entity.Person;

import java.util.Comparator;
import java.util.List;

public class PersonDao {

  private DBServiceHibernateImpl dbService;

  public PersonDao() throws Exception {
    this.dbService = new DBServiceHibernateImpl();
  }

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

  public Person findByLogin(String login) {
    return dbService.findByField(Person.class, "login", login).stream()
            .min(Comparator.comparing(Person::getLogin))
            .orElse(null);
  }
}
