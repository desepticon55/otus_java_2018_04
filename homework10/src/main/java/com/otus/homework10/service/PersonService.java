package com.otus.homework10.service;

import com.otus.homework10.dao.PersonDao;
import com.otus.homework10.entity.Person;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InjectByType;

import java.util.List;

@Component
public class PersonService {

  @InjectByType
  private PersonDao personDao;

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
