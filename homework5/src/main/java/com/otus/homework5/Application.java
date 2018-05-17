package com.otus.homework5;

import com.otus.homework5.framework.TestRunner;

public class Application {

  public static void main(String[] args) {
    TestRunner.run("com.otus.homework5.tests");
    TestRunner.run(MyTest.class);
  }
}
