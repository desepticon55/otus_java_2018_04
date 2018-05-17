package com.otus.homework5.tests;

import com.otus.homework5.framework.After;
import com.otus.homework5.framework.Assert;
import com.otus.homework5.framework.Before;
import com.otus.homework5.framework.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.otus.homework5.framework.Assert.assertEquals;
import static com.otus.homework5.framework.Assert.assertFalse;
import static com.otus.homework5.framework.Assert.assertTrue;

public class ArrayListTests {

  @Before
  public void beforeMethod() {
    System.out.println("BEFORE METHOD");
  }

  @Test
  public void addElementFromArrayListTest() {
    List<String> animals = new ArrayList<>();
    animals.add("Кит");
    animals.add("Обезьяна");
    animals.add("Акула");
    animals.add("Тигр");
    animals.add(null);
    Collections.addAll(animals, "Человек", "Петух");
    assertFalse(animals.isEmpty());
    assertEquals(7, animals.size());
  }

  @Test
  public void getElementFromArrayListTest() {
    List<String> animals = new ArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    assertFalse(animals.isEmpty());
    assertEquals("Тигр", animals.get(1));
  }

  @Test
  public void removeElementFromArrayListTest() {
    List<String> animals = new ArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    animals.add("Акула");
    animals.remove("Кит");
    assertFalse(animals.isEmpty());
    assertEquals(2, animals.size());
    assertEquals("Тигр", animals.get(0));
  }

  @Test
  public void sortElementsUseHelperCollectionsTest() {
    List<String> animals = new ArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    animals.add("Акула");
    assertFalse(animals.isEmpty());
    Collections.sort(animals);
    assertEquals("Акула", animals.get(0));
    assertEquals("Кит", animals.get(1));
    assertEquals("Тигр", animals.get(2));
  }

  @After
  public void afterMethod() {
    System.out.println("AFTER METHOD");
  }
}
