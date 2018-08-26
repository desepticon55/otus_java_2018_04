package com.otus.homework12.services;

import com.otus.homework12.annotations.Component;
import com.otus.homework12.cache.CacheEngine;
import com.otus.homework12.cache.CacheEngineImpl;

@Component
public class CacheManager {

  public <K, V> CacheEngine<K, V> createCache(int maxElements, int lifeTimeMs, int idleTimeMs) {
    return new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs);
  }
}
