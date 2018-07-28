package com.otus.homework10;

import com.otus.homework10.entity.Person;
import com.otus.homework10.entity.Phone;
import com.otus.homework10.service.PersonService;
import com.otus.homework10.service.PhoneService;
import com.otus.homework9.ObjectFactory;

import java.util.Arrays;
import java.util.List;

public class Application {

  public static void main(String[] args) {
    ObjectFactory factory = ObjectFactory.getInstance();
    factory.init(Application.class);
    PhoneService phoneService = factory.createObject(PhoneService.class);

    Person person = new Person()
            .setId(1L)
            .setFirstName("Alexey")
            .setLastName("Bodyak")
            .setAge(23)
            .setSex("мужской");
    List<Phone> phones = Arrays.asList(
            new Phone().setId(1L).setNumber("+79852458695").setPerson(person),
            new Phone().setId(2L).setNumber("+79854588888").setPerson(person));
    phones.forEach(phoneService::save);
    person.setPhones(phones);
    PersonService personService = factory.createObject(PersonService.class);
    System.out.println("Save Person method: " + personService.save(person));
    personService.findAll().forEach(System.out::println);
    System.out.println("Find Person by id: " + personService.findById(1L));
    System.out.println("Find Phone by id: " + phoneService.findById(1L));

  }
}
