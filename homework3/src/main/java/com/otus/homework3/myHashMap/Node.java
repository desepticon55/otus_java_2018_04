package com.otus.homework3.myHashMap;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class Node<K, V> implements Map.Entry<K, V> {
  @Getter
  final K key;

  @Getter
  V value;

  int hash;

  Node<K,V> next;

  private Node(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public Node(K key, V value, int hash, Node<K, V> next) {
    this.key = key;
    this.value = value;
    this.hash = hash;
    this.next = next;
  }

  public static <K, V> Node<K, V> of(K key, V value) {
    return new Node<>(key, value);
  }

  public static <K, V> Node<K, V> of(K key, V value, int hash, Node<K, V> next) {
    return new Node<>(key, value, hash, next);
  }

  @Override
  public V setValue(V value) {
    V oldValue = this.value;
    this.value = value;
    return oldValue;
  }

  public final String toString() { return key + "=" + value; }

}
