package com.otus.homework2;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTutor {
  public static void log(String s) {
    System.out.println(s);
  }

  /**
   * Замените все null в assertEquals на true или false
   */
  @Test
  public void testStringEquals() {
    String s1 = "aaa";
    String s2 = "aaa";
    s1.equals(s2);
    String s3 = new String("aaa");
    log("адрес объекта s1: " + System.identityHashCode(s1));
    log("адрес объекта s2: " + System.identityHashCode(s2));
    assertEquals(s1 == s2, true);
    assertEquals(s1.equals(s2), true);
    log("адрес объекта s3: " + System.identityHashCode(s3));
    assertEquals(s1 == s3, false);
    // метод intern() позволяет получить строку из пула строк
    String s4 = s3.intern();
    log("адрес объекта s4: " + System.identityHashCode(s4));
    assertEquals(s1 == s4, true);
    // тестируем пересоздание объекта каждый раз при изменении
    s3 = s3 + "bbb";
    log("адрес измененного объекта s3: " + System.identityHashCode(s3));
    s3 = s3.substring(0, 3); // s3 снова "aaa"
    assertEquals(s3 == s1, false);
    assertEquals(s3.equals(s1), true);
    assertEquals(s3.intern() == s1, true);
  }

  /**
   * Убедитесь, что приветствие greeting имеет вид
   * Привет, Иван Иванов!
   * или
   * Привет,Петр Первый!
   * т.е. начинается на Привет, заканчивается на !
   * и содержит 2 слова для имени и фамилии,
   * причем имя и фамилия не короче 3 букв
   * и начинаются с большой буквы
   */
  public boolean checkGreeting(String greeting) {
    if (Objects.isNull(greeting)) {
      throw new IllegalArgumentException("String should not be null");
    }

    if (greeting.isEmpty()) {
      throw new IllegalArgumentException("String should not be empty");
    }

    if (!greeting.endsWith("!")) {
      return false;
    }
    greeting = greeting.replaceAll(", ", ",");
    greeting = greeting.replaceAll("!", "").trim();
    greeting = greeting.replaceAll(" ", ",");
    String[] arr = greeting.split(",");
    if (arr.length < 3) {
      return false;
    }

    if (arr[1].length() < 3 || arr[2].length() < 3) {
      return false;
    }
    Pattern pattern = Pattern.compile("(Привет,[А-Я][a-я]*[,][А-Я][а-я\\s]*)");
    Matcher matcher = pattern.matcher(greeting);
    return matcher.find();
  }

  @Test
  public void testCheckGreeting() {
    assertTrue(checkGreeting("Привет, Иван Иванов!"));
    assertTrue(checkGreeting("Привет,Петр Первый!"));
    assertTrue(checkGreeting("Привет, Петр Первый!"));
    assertTrue(checkGreeting("Привет, Петр Первый !"));

    assertFalse("В начале должно быть слово Привет и запятая",
            checkGreeting("Здравствуйте, Петр Первый!"));
    assertFalse("В конце должен быть восклицательный знак",
            checkGreeting("Привет, Петр Первый"));
    assertFalse("Имя слишком короткое",
            checkGreeting("Привет, Ли Сунь!"));
    assertFalse("Фамилия слишком короткая",
            checkGreeting("Привет, Куй Ли !"));
    assertFalse("Должны быть указаны и имя, и фамилия",
            checkGreeting("Привет, Петр!"));
    assertFalse("Первая буква имени должна быть заглавной",
            checkGreeting("Привет, петр Первый!"));
    assertFalse("Первая буква фамилии должна быть заглавной",
            checkGreeting("Привет, Петр первый!"));
  }
}
