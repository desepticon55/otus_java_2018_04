package com.otus.homework14;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class Sorter implements Runnable {
  private final int[] array;

  Sorter(int[] array) {
    this.array = array;
  }

  @Override
  public void run() {
    Arrays.sort(array);
  }
}
