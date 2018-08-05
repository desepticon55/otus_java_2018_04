package com.otus.homework11.services;

import com.otus.homework11.annotations.Component;
import com.otus.homework11.annotations.InjectByType;
import com.otus.homework11.dao.PersonDao;
import com.otus.homework11.entity.Person;

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
