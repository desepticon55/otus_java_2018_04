package com.otus.homework1;

import com.google.common.base.Joiner;

public class Main {
  public static void main(String[] args) {
    String s = Joiner.on(",").join(1, 2, 3);
    System.out.println("Hello World");
    System.out.println(s);
  }
}
