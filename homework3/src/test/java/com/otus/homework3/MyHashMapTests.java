package com.otus.homework3;

import com.otus.homework3.myHashMap.MyHashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyHashMapTests {

  @Test
  public void addElementToHashMap() {
    Map<String, String> peoples = new MyHashMap<>();
    peoples.put("i.ivanov", "Иванов Иван Иванович");
    peoples.put("s.sidorov", "Сидоров Сергей Викторович");
    for (int i = 0; i < 150; i++) {
      peoples.put("s.sidorov" + i, "Сидоров Сергей Викторович");
    }
    peoples.put("m.korneev", "Корнеев Михаил Иванович");
    assertFalse(peoples.isEmpty());
    assertEquals(peoples.size(), 153);
  }

  @Test
  public void getElementFromHashMap() {
    Map<String, String> peoples = new MyHashMap<>();
    peoples.put("i.ivanov", "Иванов Иван Иванович");
    peoples.put("s.sidorov", "Сидоров Сергей Викторович");
    peoples.put("m.korneev", "Корнеев Михаил Иванович");
    assertFalse(peoples.isEmpty());
    assertEquals(peoples.size(), 3);
    assertEquals(peoples.get("s.sidorov"), "Сидоров Сергей Викторович");
  }
}
