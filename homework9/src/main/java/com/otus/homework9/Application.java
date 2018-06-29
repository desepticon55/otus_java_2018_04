package com.otus.homework9;

import com.otus.homework9.config.LocalH2DataSource;
import com.otus.homework9.entity.DataSet;
import com.otus.homework9.entity.EmployeeData;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import static com.otus.homework9.config.Configurator.configurator;

public class Application {

  public static void main(String[] args) {
    Executor executor = new MyExecutor();
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
