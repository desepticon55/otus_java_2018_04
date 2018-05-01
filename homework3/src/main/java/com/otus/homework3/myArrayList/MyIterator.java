package com.otus.homework3.myArrayList;

import java.util.Iterator;

public class MyIterator<T> implements Iterator<T> {

  protected MyArrayList<T> list;
  protected int counter = 0;
  protected int lastElementIndex = -1;

  public MyIterator() {}

  public MyIterator(MyArrayList<T> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return counter != list.size;
  }

  @Override
  public T next() {
    T value = list.values[counter];
    lastElementIndex = counter;
    counter++;
    return value;
  }

  @Override
  public void remove() {
    list.remove(lastElementIndex);
  }
}
