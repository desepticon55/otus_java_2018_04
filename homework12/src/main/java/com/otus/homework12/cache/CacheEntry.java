package com.otus.homework12.cache;

import lombok.Getter;

import java.lang.ref.SoftReference;

@Getter
public class CacheEntry<K, V> {
  private final K key;
  private final SoftReference<V> value;
  private final long creationTime;
  private long lastAccessTime;


  private CacheEntry(K key, V value) {
    this.key = key;
    this.value = new SoftReference<>(value);
    this.creationTime = System.currentTimeMillis();
    this.lastAccessTime = System.currentTimeMillis();
  }

  public static <K, V> CacheEntry<K, V> of(K key, V value) {
    return new CacheEntry<K, V>(key, value);
  }

  public void setAccessed() {
    lastAccessTime = System.currentTimeMillis();
  }
}
