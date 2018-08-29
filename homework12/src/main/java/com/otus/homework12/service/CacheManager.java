package com.otus.homework12.service;

import com.otus.homework12.cache.CacheEngine;
import com.otus.homework12.cache.CacheEngineImpl;

public class CacheManager {

  public <K, V> CacheEngine<K, V> createCacheEngine(int maxElements, int lifeTimeMs, int idleTimeMs) {
    return new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs);
  }
}
