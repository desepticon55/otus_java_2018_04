package com.otus.homework10;

import com.otus.homework9.ObjectFactory;
import com.otus.homework9.services.Executor;
import com.otus.homework9.services.ExecutorService;
import org.fluttercode.datafactory.impl.DataFactory;

public class Application {

  public static void main(String[] args) {
//    ObjectFactory factory = ObjectFactory.getInstance();
//    factory.init(Application.class);
//    Person person = new Person()
//            .setFirstName("Alexey")
//            .setLastName("Bodyak")
//            .setAge(23)
//            .setSex("мужской");
//    Executor executor = factory.createObject(ExecutorService.class);
//    executor.save(person);
//    System.out.println(executor.load(1L, Person.class));
    DataFactory dataFactory = new DataFactory();
    System.out.println(dataFactory.getRandomChar());
    System.out.println(dataFactory.getRandomChars(4, 8));
    System.out.println(dataFactory.getRandomText(15));
    System.out.println(dataFactory.getRandomText(8, 20));
    System.out.println(dataFactory.getRandomWord(8,20));
    System.out.println(dataFactory.getRandomWord());
    System.out.println(dataFactory.getNumberUpTo(89));
    System.out.println(dataFactory.getNumberBetween(18, 25));
  }
}
