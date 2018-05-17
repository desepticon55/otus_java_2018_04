package com.otus.homework4;

import java.lang.management.ManagementFactory;

public class Application {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("PID=" + ManagementFactory.getRuntimeMXBean().getName());
    GCLogExecutor leakLogExecutor = new MemoryLeakLogExecutor();
    leakLogExecutor.execute();
  }
}
