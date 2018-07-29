package com.otus.homework14;

import org.junit.Test;

import java.util.Arrays;

import static com.otus.homework14.ArrayUtils.*;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class SortTest {

  @Test
  public void test() {
    int[] array1 = getRandomArray(37);
    int[] array2 = Arrays.copyOf(array1, array1.length);

    System.out.println("Not sorted array:");
    System.out.println(Arrays.toString(array1));

    Arrays.sort(array1);
    System.out.println("Sorted array:");
    System.out.println(Arrays.toString(array1));

    new SorterService().sort(array2);
    System.out.println("Multi Sorted array:");
    System.out.println(Arrays.toString(array2));

    assertArrayEquals(array1, array2);
  }
}
