package com.otus.homework14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SorterService {
  private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

  public void sort(int[] array) {
    if (array.length < NUMBER_OF_THREADS) {
      Arrays.sort(array);
    } else {
      multiSort(array);
    }
  }

  private void multiSort(int[] array) {
    Map<Thread, Runnable> sortersMap = createThreadsMap(array);

    sortersMap.keySet().forEach(Thread::run);

    sortersMap.keySet().forEach(el -> {
      try {
        el.join();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    int[] mergeSorters = mergeSorters(sortersMap.values().toArray(new Sorter[0]));

    System.arraycopy(mergeSorters, 0, array, 0, array.length);
  }

  private Map<Thread, Runnable> createThreadsMap(int[] array) {
    int elemInArray = array.length / NUMBER_OF_THREADS;

    Map<Thread, Runnable> result = new HashMap<>();

    for (int i = 0; i < NUMBER_OF_THREADS; i++) {
      int start = i * elemInArray;
      int end = (i + 1) * elemInArray;
      if (isLastIter(i)) {
        end = array.length;
      }
      Sorter sorter = new Sorter(Arrays.copyOfRange(array, start, end));
      result.put(new Thread(sorter), sorter);
    }

    return result;
  }

  private boolean isLastIter(int i) {
    return (i + 1) == NUMBER_OF_THREADS;
  }

  private int[] mergeSorters(Runnable[] sorters) {
    int[] mergeAr = ((Sorter) sorters[0]).getArray();
    for (int i = 1; i < NUMBER_OF_THREADS; i++) {
      mergeAr = ArrayUtils.merge(mergeAr,((Sorter) sorters[i]).getArray());
    }
    return mergeAr;
  }
}
