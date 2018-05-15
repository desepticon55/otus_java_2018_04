package com.otus.homework4;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GCLogExecutorImpl implements GCLogExecutor {

  private ScheduledExecutorService executorService;

  GCLogExecutorImpl() {
    executorService = Executors.newSingleThreadScheduledExecutor();
  }

  @Override
  public void execute() {
    executorService.scheduleAtFixedRate(() -> {
      Object[] objects = new Object[5000000];
      for (int i = 0; i < objects.length; i++) {
        objects[i] = 1;
      }
    }, 100, 100, TimeUnit.MILLISECONDS);
    executorService.scheduleAtFixedRate(this::printGCStats, 2, 2, TimeUnit.SECONDS);
  }
}
