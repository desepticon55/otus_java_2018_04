package com.otus.homework4;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public interface GCLogExecutor {
  void execute() throws InterruptedException;
}
