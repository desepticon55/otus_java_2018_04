package com.otus.homework5.tests;

import com.otus.homework5.framework.Assert;
import com.otus.homework5.framework.Test;

import java.util.HashMap;
import java.util.Map;

import static com.otus.homework5.framework.Assert.assertEquals;
import static com.otus.homework5.framework.Assert.assertFalse;

public class HashMapTests {
  @Test
  public void addElementToHashMap() {
    Map<String, String> peoples = new HashMap<>();
    peoples.put("i.ivanov", "Иванов Иван Иванович");
    peoples.put("s.sidorov", "Сидоров Сергей Викторович");
    for (int i = 0; i < 150; i++) {
      peoples.put("s.sidorov" + i, "Сидоров Сергей Викторович");
    }
    peoples.put("m.korneev", "Корнеев Михаил Иванович");
    assertFalse(peoples.isEmpty());
    assertEquals(153, peoples.size());
  }

  @Test
  public void getElementFromHashMap() {
    Map<String, String> peoples = new HashMap<>();
    peoples.put("i.ivanov", "Иванов Иван Иванович");
    peoples.put("s.sidorov", "Сидоров Сергей Викторович");
    peoples.put("m.korneev", "Корнеев Михаил Иванович");
    assertFalse(peoples.isEmpty());
    assertEquals(3, peoples.size());
    assertEquals("Сидоров Сергей Викторович", peoples.get("s.sidorov"));
  }

}
