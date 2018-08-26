package com.otus.homework12.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
  private static final int TIME_THRESHOLD_MS = 5;

  private final int maxElements;
  private final long lifeTimeMs;
  private final long idleTimeMs;

  private final Map<K, CacheEntry<K, V>> elements = new LinkedHashMap<>();
  private final Timer timer = new Timer();

  private int hit = 0;
  private int miss = 0;

  public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs) {
    this.maxElements = maxElements;

    if (lifeTimeMs > 0 && idleTimeMs > 0) {
      throw new RuntimeException("Плохая практика использовать idleTime совместно с lifeTime");
    }

    this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
    this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
  }

  @Override
  public void put(K entityKey, V entityValue) {
    CacheEntry<K, V> element = CacheEntry.of(entityKey, entityValue);
    if (elements.size() == maxElements) {
      K firstKey = elements.keySet().iterator().next();
      elements.remove(firstKey);
    }

    K key = element.getKey();
    elements.put(key, element);

    if (lifeTimeMs != 0) {
      TimerTask lifeTimerTask = getTimerTask(key, lifeElement ->
              lifeElement.getCreationTime() + lifeTimeMs);
      timer.schedule(lifeTimerTask, lifeTimeMs);
    }
    if (idleTimeMs != 0) {
      TimerTask idleTimerTask = getTimerTask(key, idleElement ->
              idleElement.getLastAccessTime() + idleTimeMs);
      timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
    }
  }

  @Override
  public V get(K key) {
    CacheEntry<K, V> element = elements.get(key);
    if (element != null) {
      V result = element.getValue().get();
      if (result == null) {
        elements.remove(element.getKey());
        miss++;
      } else {
        element.setAccessed();
        hit++;
      }
      return result;
    } else {
      miss++;
      return null;
    }
  }

  @Override
  public int getHitCount() {
    return hit;
  }

  @Override
  public int getMissCount() {
    return miss;
  }

  @Override
  public void dispose() {
    timer.cancel();
  }

  private TimerTask getTimerTask(final K key, Function<CacheEntry<K, V>, Long> timeFunction) {
    return new TimerTask() {
      @Override
      public void run() {
        CacheEntry<K, V> element = elements.get(key);
        if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
          elements.remove(key);
          this.cancel();
        }
      }
    };
  }

  private boolean isT1BeforeT2(long t1, long t2) {
    return t1 < t2 + TIME_THRESHOLD_MS;
  }
}
