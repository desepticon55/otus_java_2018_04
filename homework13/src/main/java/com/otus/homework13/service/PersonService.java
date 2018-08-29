package com.otus.homework13.service;

import com.otus.homework13.cache.CacheEngine;
import com.otus.homework13.dao.PersonDao;
import com.otus.homework13.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

  @Autowired
  private PersonDao personDao;
  @Autowired
  private CacheManager cacheManager;
  private CacheEngine<String, Person> personCacheEngine;

  @PostConstruct
  public void init() {
    this.personCacheEngine = cacheManager.createCacheEngine(5000, 0, 10000);
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
