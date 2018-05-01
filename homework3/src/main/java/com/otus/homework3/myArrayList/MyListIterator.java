package com.otus.homework3.myArrayList;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyListIterator<T> extends MyIterator<T> implements ListIterator<T> {


  public MyListIterator(int index, MyArrayList<T> list) {
    super(list);
    counter = index;
  }

  @Override
  public boolean hasNext() {
    return super.hasNext();
  }

  @Override
  public T next() {
    return super.next();
  }

  @Override
  public boolean hasPrevious() {
    return counter != 0;
  }

  @Override
  public T previous() {
    int index = counter - 1;
    if (index < 0) {
      throw new NoSuchElementException();
    }
    counter = index;
    lastElementIndex = index;
    return list.values[counter];
  }

  @Override
  public int nextIndex() {
    return counter;
  }

  @Override
  public int previousIndex() {
    return counter - 1;
  }

  @Override
  public void remove() {
    list.remove(lastElementIndex);
  }

  @Override
  public void set(T t) {
    list.set(lastElementIndex, t);
  }

  @Override
  public void add(T t) {
    list.add(t);
  }
}
