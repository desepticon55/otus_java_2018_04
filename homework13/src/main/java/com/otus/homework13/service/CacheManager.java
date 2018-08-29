package com.otus.homework13.service;

import com.otus.homework13.cache.CacheEngine;
import com.otus.homework13.cache.CacheEngineImpl;
import lombok.NoArgsConstructor;
import org.osgi.service.component.annotations.Component;
import org.springframework.stereotype.Service;

@Service
public class CacheManager {

  public <K, V> CacheEngine<K, V> createCacheEngine(int maxElements, int lifeTimeMs, int idleTimeMs) {
    return new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs);
  }
}
