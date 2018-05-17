package com.otus.homework4;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MemoryLeakLogExecutor implements GCLogExecutor {
  private ScheduledExecutorService executorService;

  MemoryLeakLogExecutor() {
    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(this::printGCStats, 10, 10, TimeUnit.SECONDS);
  }

  @Override
  public void execute() throws InterruptedException {
    List<Integer> integerList = new ArrayList<>();
    List<Integer> integers = new Random()
            .ints(5000000, 1, 3000)
            .boxed()
            .collect(Collectors.toList());
    while(true) {
      integerList.addAll(integers);
      integerList.removeIf(el -> el % 10 == 0);
      System.out.println("List size = " + integerList.size());
      TimeUnit.SECONDS.sleep(7);
    }
  }

  private void printGCStats() {
    long totalGCCount = 0;
    long totalGCTime = 0;
    List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
    System.out.println("Young Garbage Collection Count: " + garbageCollectorMXBeans.get(0).getCollectionCount());
    System.out.println("Young Garbage Collection Time (ms):" + garbageCollectorMXBeans.get(0).getCollectionTime());
    System.out.println("Old Garbage Collection Count: " + garbageCollectorMXBeans.get(1).getCollectionCount());
    System.out.println("Old Garbage Collection Time (ms): " + garbageCollectorMXBeans.get(1).getCollectionTime());

    for (GarbageCollectorMXBean gc : garbageCollectorMXBeans) {

      long count = gc.getCollectionCount();
      if (count >= 0) {
        totalGCCount += count;
      }

      long time = gc.getCollectionTime();
      if (time >= 0) {
        totalGCTime += time;
      }
    }

    System.out.println("Total Garbage Collection Count: " + totalGCCount);
    System.out.println("Total Garbage Collection Time (ms): " + totalGCTime);
    System.out.println("------------------------------------");
  }
}
