package com.otushomework8.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Map;

@Data
public class TestObject {
  int i = 3;
  int[] integerArray = {1,456,7,234,23};
  double d = 3.4;
  double[] doubleArray = {3.,4.3,45.213,32.1};
  List<String> stringList = Lists.newArrayList("first", "second", "third");
  Employee employee = Employee.builder()
          .firstName("Alexey")
          .lastName("Bodyak")
          .age(23)
          .lead(Employee.builder().firstName("Tolmachev").lastName("Andrey").age(33).build())
          .build();
  Map<Integer, String> map = ImmutableMap.<Integer, String>builder()
          .put(1, "Alexey")
          .put(2, "Andrey")
          .put(3, "Victor")
          .build();
}
