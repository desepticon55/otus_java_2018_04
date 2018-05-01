package com.otus.homework3.myHashMap;

import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Nonnull;
import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

  static final int DEFAULT_INITIAL_CAPACITY = 16;

  transient Node<K, V>[] values;
  int size = 0;

  public MyHashMap() {
    values = new Node[DEFAULT_INITIAL_CAPACITY];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean containsValue(Object value) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public V get(Object key) {
    Objects.requireNonNull(key, "Key should be not null");

    int hash = hash(key);
    int idx = indexFor(hash, values.length);
    for (Node<K,V> e = values[idx]; e != null; e = e.next) {
      Object k = e.key;
      if (e.hash == hash && (k == key || key.equals(k))) {
        return e.value;
      }
    }

    return null;
  }

  @Override
  public V put(K key, V value) {
    Objects.requireNonNull(key, "Key should be not null");

    int hash = hash(key);
    int idx = indexFor(hash, values.length);
    for (Node<K,V> e = values[idx]; e != null; e = e.next) {
      Object k = e.key;
      if (e.hash == hash && (k == key || key.equals(k))) {
        V oldValue = e.value;
        e.value = value;
        return oldValue;
      }
    }

    addEntry(hash, key, value, idx);
    return value;
  }

  @Override
  public V remove(Object key) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public void clear() {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  @Nonnull
  public Set<K> keySet() {
    Set<K> entries = new HashSet<>();
    for (Node<K, V> value : values) {
      for (Entry<K, V> e = value; e != null; e = ((Node<K, V>) e).next) {
        entries.add(e.getKey());
      }
    }
    return entries;
  }

  @Override
  @Nonnull
  public Collection<V> values() {
    List<V> entries = new ArrayList<>();
    for (Node<K, V> value : values) {
      for (Entry<K, V> e = value; e != null; e = ((Node<K, V>) e).next) {
        entries.add(e.getValue());
      }
    }
    return entries;
  }

  @Override
  @Nonnull
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    for (Node<K, V> value : values) {
      for (Entry<K, V> e = value; e != null; e = ((Node<K, V>) e).next) {
        entries.add(e);
      }
    }
    return entries;
  }

  private void addEntry(int hash, K key, V value, int bucketIndex) {
    Node<K,V> e = values[bucketIndex];
    values[bucketIndex] = Node.of(key, value, hash, e);
    size++;
  }

  private int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  private int indexFor(int hash, int length) {
    return Math.abs(hash % length);
  }
}
