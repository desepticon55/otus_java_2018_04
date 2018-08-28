package com.otus.homework12;

import com.otus.homework12.entity.Person;
import com.otus.homework12.entity.Phone;
import com.otus.homework12.service.PersonService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Arrays;
import java.util.List;

@WebListener
public class StartStopListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      Person person = new Person()
              .setId(1L)
              .setFirstName("Alexey")
              .setLastName("Bodyak")
              .setAge(23)
              .setSex("мужской");
      List<Phone> phones = Arrays.asList(
              new Phone().setId(1L).setNumber("+79852458695").setPerson(person),
              new Phone().setId(2L).setNumber("+79854588888").setPerson(person));
      person.setPhones(phones);
      PersonService personService = new PersonService();
      System.out.println("Save Person method: " + personService.save(person));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
