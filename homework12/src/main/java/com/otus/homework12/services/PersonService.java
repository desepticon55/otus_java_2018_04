package com.otus.homework12.services;

import com.otus.homework12.annotations.Component;
import com.otus.homework12.annotations.InjectByType;
import com.otus.homework12.dao.PersonDao;
import com.otus.homework12.entity.Person;

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
