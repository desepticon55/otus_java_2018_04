package com.otus.homework11.test;

import com.otus.homework11.entity.Person;

public class DataFactory {

  public static Person createEmployee(Long id) {
    Person person = new Person().setName("Alexey").setAge(23);
    person.setId(id);
    return person;
  }
}
