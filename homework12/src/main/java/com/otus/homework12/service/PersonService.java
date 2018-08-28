package com.otus.homework12.service;

import com.otus.homework12.dao.PersonDao;
import com.otus.homework12.entity.Person;

import java.util.List;

public class PersonService {

  private final PersonDao personDao;

  public PersonService() throws Exception {
    this.personDao = new PersonDao();
  }

  public List<Person> findAll() {
    return personDao.findAll();
  }

  public Boolean save(Person person) {
    return personDao.save(person);
  }

  public Person findById(Long id) {
    return personDao.findById(id);
  }
}
