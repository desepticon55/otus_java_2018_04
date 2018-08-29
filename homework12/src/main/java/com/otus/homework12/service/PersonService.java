package com.otus.homework12.service;

import com.otus.homework12.cache.CacheEngine;
import com.otus.homework12.dao.PersonDao;
import com.otus.homework12.entity.Person;

import java.util.List;
import java.util.Objects;

public class PersonService {

  private final static PersonService INSTANCE = new PersonService();

  private PersonDao personDao;
  private CacheManager cacheManager;
  private CacheEngine<String, Person> personCacheEngine;

  public static PersonService getInstance() {
    return INSTANCE;
  }

  public PersonService() {
    try {
      this.personDao = new PersonDao();
      this.cacheManager = new CacheManager();
      this.personCacheEngine = cacheManager.createCacheEngine(5000, 0, 10000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Person> findAll() {
    return personDao.findAll();
  }

  public Boolean save(Person person) {
    return personDao.save(person);
  }

  public Person findByLogin(String login) {
    Person person = personCacheEngine.get(login);
    if (Objects.isNull(person)) {
      person = personDao.findByLogin(login);
      personCacheEngine.put(login, person);
    }
    return person;
  }

  public CacheEngine<String, Person> getPersonCacheEngine() {
    return personCacheEngine;
  }
}
