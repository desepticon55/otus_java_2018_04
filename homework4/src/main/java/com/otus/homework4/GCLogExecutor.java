package com.otus.homework4;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public interface GCLogExecutor {
  void execute() throws InterruptedException;

  default void printGCStats() {
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
