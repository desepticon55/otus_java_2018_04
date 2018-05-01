package com.otus.homework3;

import com.google.common.base.Joiner;
import com.otus.homework3.myArrayList.MyArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyArrayListTests {

  @Test
  public void addElementFromArrayListTest() {
    List<String> animals = new MyArrayList<>();
    animals.add("Кит");
    animals.add("Обезьяна");
    animals.add("Акула");
    animals.add("Тигр");
    animals.add(null);
    Collections.addAll(animals, "Человек", "Петух");
    assertFalse(animals.isEmpty());
    assertEquals(animals.size(), 7);
  }

  @Test
  public void getElementFromArrayListTest() {
    List<String> animals = new MyArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    assertFalse(animals.isEmpty());
    assertEquals(animals.get(1), "Тигр");
  }

  @Test
  public void removeElementFromArrayListTest() {
    List<String> animals = new MyArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    animals.add("Акула");
    animals.remove("Кит");
    animals.remove(2);
    assertFalse(animals.isEmpty());
    assertEquals(animals.size(), 1);
    assertEquals(animals.get(0), "Тигр");
  }

  @Test
  public void sortElementsUseHelperCollectionsTest() {
    List<String> animals = new MyArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    animals.add("Акула");
    assertFalse(animals.isEmpty());
    Collections.sort(animals);
    assertEquals(animals.get(0), "Акула");
    assertEquals(animals.get(1), "Кит");
    assertEquals(animals.get(2), "Тигр");
  }

  @Test
  public void copyElementUseHelperCollectionsTest() {
    List<String> animals = new MyArrayList<>();
    animals.add("Кит");
    animals.add("Тигр");
    animals.add("Акула");
    List<String> animalsCopy = new MyArrayList<>();
    Collections.addAll(animalsCopy, "Овца", "Змея", "Медведь", "Лиса", "Заяц", "Волк", "Тигр", "Куропатка");
    assertFalse(animalsCopy.isEmpty());
    Collections.copy(animalsCopy, animals);
    assertFalse(animalsCopy.isEmpty());
    assertEquals(animalsCopy.get(0), "Кит");
  }


}
