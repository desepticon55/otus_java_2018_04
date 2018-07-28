package com.otus.homework9;

import com.otus.homework9.entity.Address;
import com.otus.homework9.entity.EmployeeData;
import com.otus.homework9.services.Executor;
import com.otus.homework9.services.ExecutorService;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Application {

  public static void main(String[] args) throws SQLException {

    ObjectFactory objectFactory = ObjectFactory.getInstance();
    objectFactory.init(Application.class);
    Executor executor = objectFactory.createObject(ExecutorService.class);
    executor.save(new EmployeeData().setName("Alexey").setAge(23));
    executor.save(new EmployeeData().setName("Viktor").setAge(15));
    executor.save(new EmployeeData().setName("Alexander").setAge(50));
    executor.save(new EmployeeData().setName("Maxim").setAge(80));
    Address address = new Address().setCity("Moscow").setStreet("Some street");
    address.setId(1L);
    executor.save(address);
    executor.save(new EmployeeData().setName("Denis").setAge(25).setAddress(address));
    List<EmployeeData> employeeDataSet = executor.load(EmployeeData.class);
    assert !employeeDataSet.isEmpty();
    employeeDataSet.forEach(System.out::println);
    EmployeeData employeeById = executor.load(5L, EmployeeData.class);
    assert Objects.nonNull(employeeById);
    System.out.println(employeeById);
  }
}
