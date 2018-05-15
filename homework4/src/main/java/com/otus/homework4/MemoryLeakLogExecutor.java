package com.otus.homework4;

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
  }

  @Override
  public void execute() throws InterruptedException {
    List<Integer> integerList = new ArrayList<>();
    List<Integer> integers = new Random()
            .ints(5000000, 1, 3000)
            .boxed()
            .collect(Collectors.toList());
    executorService.scheduleAtFixedRate(this::printGCStats, 1, 1, TimeUnit.SECONDS);
    while(true) {
      integerList.addAll(integers);
      integerList.removeIf(el -> el % 10 == 0);
      System.out.println("List size = " + integerList.size());
      TimeUnit.SECONDS.sleep(7);
    }
  }
}
