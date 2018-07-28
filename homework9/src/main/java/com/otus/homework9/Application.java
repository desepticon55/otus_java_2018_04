package com.otus.homework9;

import com.otus.homework9.entity.EmployeeData;
import com.otus.homework9.services.Executor;
import com.otus.homework9.services.ExecutorService;

import java.util.List;
import java.util.Objects;

public class Application {

  public static void main(String[] args) {
    ObjectFactory objectFactory = ObjectFactory.getInstance();
    objectFactory.init(Application.class);
    Executor executor = objectFactory.createObject(ExecutorService.class);
    executor.save(new EmployeeData().setName("Alexey").setAge(23));
    executor.save(new EmployeeData().setName("Viktor").setAge(15));
    executor.save(new EmployeeData().setName("Alexander").setAge(50));
    executor.save(new EmployeeData().setName("Maxim").setAge(80));
    List<EmployeeData> employeeDataSet = executor.load(EmployeeData.class);
    assert !employeeDataSet.isEmpty();
    employeeDataSet.forEach(System.out::println);
    EmployeeData employeeById = executor.load(employeeDataSet.get(0).getId(), EmployeeData.class);
    assert Objects.nonNull(employeeById);
    System.out.println(employeeById);
  }
}
