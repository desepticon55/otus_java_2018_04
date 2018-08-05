package com.otus.homework11.dao;

import com.otus.homework11.annotations.Component;
import com.otus.homework11.annotations.InitMethod;
import com.otus.homework11.annotations.InjectByType;
import com.otus.homework11.cache.CacheEngine;
import com.otus.homework11.entity.Person;
import com.otus.homework11.services.CacheManager;
import com.otus.homework11.services.DBService;

import java.util.List;
import java.util.Objects;

@Component
public class PersonDao {

  @InjectByType
  private DBService dbService;
  @InjectByType
  private CacheManager cacheManager;
  private CacheEngine<Long, Person> personCacheEngine;

  @InitMethod
  public void init() {
    personCacheEngine = cacheManager.createCache(5000, 0, 10000);
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
    Person person = personCacheEngine.get(id);
    if (Objects.isNull(person)) {
      person = dbService.findById(id, Person.class);
      personCacheEngine.put(id, person);
    }
    return person;
  }
}
