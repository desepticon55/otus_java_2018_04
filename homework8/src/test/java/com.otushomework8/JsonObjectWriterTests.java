package com.otushomework8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otushomework8.entity.Employee;
import org.junit.Test;

public class JsonObjectWriterTests {

  @Test
  public void writeObjectToJsonTest() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Employee employee = Employee.builder()
            .firstName("Alexey")
            .lastName("Bodyak")
            .age(23)
            .build();
    String json = gson.toJson(employee);
    System.out.println(json);
  }
}
