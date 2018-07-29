package com.otus.homework14;

import java.util.Random;

public class ArrayUtils {
  public static int[] getRandomArray(int length) {
    int[] array = new int[length];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      array[i] = random.nextInt(100);
    }
    return array;
  }

  public static int[] merge(int[] a, int[] b) {

    int[] answer = new int[a.length + b.length];
    int i = 0, j = 0, k = 0;

    while (i < a.length && j < b.length)
      answer[k++] = a[i] < b[j] ? a[i++] :  b[j++];

    while (i < a.length)
      answer[k++] = a[i++];


    while (j < b.length)
      answer[k++] = b[j++];

    return answer;
  }
}
