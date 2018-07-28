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
    List<Phone> phones = Arrays.asList(new Phone()
            .setNumber("+79852458695"), new Phone().setNumber("+79854588888"));
    phones.forEach(phoneService::save);
    phoneService.findAll().forEach(System.out::println);
    System.out.println("Find Phone by id: " + phoneService.findById(1L));

    PersonService personService = factory.createObject(PersonService.class);
    Person person = new Person()
            .setFirstName("Alexey")
            .setLastName("Bodyak")
            .setAge(23)
            .setPhones(phones)
            .setSex("мужской");
    System.out.println("Save Person method: " + personService.save(person));
    personService.findAll().forEach(System.out::println);
    System.out.println("Find Person by id: " + personService.findById(1L));

  }
}
