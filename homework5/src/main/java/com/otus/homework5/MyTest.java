package com.otus.homework5;

import com.otus.homework5.framework.Assert;
import com.otus.homework5.framework.Test;

import static com.otus.homework5.framework.Assert.assertTrue;

public class MyTest {

  @Test
  public void firstTest() {
    String s = getClass().getCanonicalName();
    assertTrue(!s.isEmpty());
  }
}
