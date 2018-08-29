package com.otus.homework13.dao;

import com.otus.homework13.DBServiceHibernate;
import com.otus.homework13.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class PersonDao {

  @Autowired
  private DBServiceHibernate dbService;

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
