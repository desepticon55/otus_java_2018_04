package com.otus.homework11;

import com.otus.homework11.entity.Address;
import com.otus.homework11.entity.Person;
import com.otus.homework11.services.AddressService;
import com.otus.homework11.services.PersonService;

import java.util.List;
import java.util.Objects;

public class Application {

  public static void main(String[] args) {
    ObjectFactory objectFactory = ObjectFactory.getInstance();
    objectFactory.init(Application.class);
    PersonService personService = objectFactory.createObject(PersonService.class);
    personService.save(new Person().setName("Alexey").setAge(23));
    personService.save(new Person().setName("Viktor").setAge(15));
    personService.save(new Person().setName("Alexander").setAge(50));
    personService.save(new Person().setName("Maxim").setAge(80));

    AddressService addressService = objectFactory.createObject(AddressService.class);
    Address address = new Address().setCity("Moscow").setStreet("Some street");
    address.setId(1L);
    addressService.save(address);
    personService.save(new Person().setName("Denis").setAge(25).setAddress(address));
    List<Person> employeeDataSet = personService.findAll();
    assert !employeeDataSet.isEmpty();
    employeeDataSet.forEach(System.out::println);
    System.out.println("========================================");
    Person employeeById = personService.findById(5L);
    assert Objects.nonNull(employeeById);
    System.out.println(employeeById);
    Person employeeByIdFromCache = personService.findById(5L);
    assert Objects.nonNull(employeeByIdFromCache);
    System.out.println(employeeByIdFromCache);
    System.exit(0);
  }
}
