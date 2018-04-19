package com.otus.homework;

import com.google.common.base.Joiner;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String s = Joiner.on(",").join(1, 2, 3);
    System.out.println("Hello World");
    System.out.println(s);

    Pair<Integer, String> pair = Pair.of(1, "Hello World" );
    System.out.println(pair);

    getMiddleInLinkedList();
  }

  private static void getMiddleInLinkedList() {
    List<String> linkedList = new LinkedList<>();
    linkedList.add("1");
    linkedList.add("2");
    linkedList.add("3");
    linkedList.add("4");
    linkedList.add("5");
    linkedList.add("6");
    linkedList.add("7");
    linkedList.add("8");
    linkedList.add("9");
    linkedList.add("10");
    Iterator<String> iterator = linkedList.iterator();
    Iterator<String> iterator1 = linkedList.iterator();
    int i = 0;
    String middle = null;
    while (iterator1.hasNext()) {
      if (i % 2 == 0) {
        middle = iterator.next();
      }
      i++;
      if (iterator1.hasNext()) {
        iterator1.next();
      }
    }
    System.out.println("length of LinkedList: " + linkedList.size());
    System.out.println("middle element of LinkedList : " + middle);
  }
}
