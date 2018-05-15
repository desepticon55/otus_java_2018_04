package com.otus.homework3.myArrayList;

import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Nonnull;
import java.util.*;

public class MyArrayList<T> implements List<T> {

  private final Integer DEFAULT_CAPACITY = 16;

  private T[] values;

  private int size = 0;

  public MyArrayList(T[] values) {
    this.values = values;
  }

  public MyArrayList() {
    this.values = ((T[]) new Object[DEFAULT_CAPACITY]);
  }

  public MyArrayList(int capacity) {
    this.values = ((T[]) new Object[capacity]);
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
  public boolean contains(Object o) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  @Nonnull
  public Object[] toArray() {
    return Arrays.copyOf(values, size);
  }

  @Override
  @Nonnull
  public <T1> T1[] toArray(@Nonnull T1[] a) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean add(T t) {
    if (size == values.length) {
      T[] newArray = (T[]) new Object[values.length * 2];
      System.arraycopy(values, 0, newArray, 0, values.length);
      values = newArray;
    }
    values[size] = t;
    size++;
    return true;
  }

  @Override
  public boolean remove(Object o) {
    if (Objects.isNull(o)) {
      for (int i = 0; i < size; i++) {
        if (values[i] == null) {
          fastRemove(i);
          return true;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (values[i].equals(o)) {
          fastRemove(i);
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(@Nonnull Collection<?> c) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean addAll(@Nonnull Collection<? extends T> c) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean addAll(int index, @Nonnull Collection<? extends T> c) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean removeAll(@Nonnull Collection<?> c) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> c) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public void clear() {
    for (int i = 0; i < values.length; i++) {
      values[i] = null;
    }

    size = 0;
  }

  @Override
  public T get(int index) {
    return values[index];
  }

  @Override
  public T set(int index, T element) {
    if (index > size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    T oldValue = get(index);
    values[index] = element;
    return oldValue;
  }

  @Override
  public void add(int index, T element) {

  }

  @Override
  public T remove(int index) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    T deletedValue = values[index];
    fastRemove(index);
    return deletedValue;
  }

  @Override
  public int indexOf(Object o) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Override
  @Nonnull
  public ListIterator<T> listIterator() {
    return listIterator(0);
  }

  @Override
  @Nonnull
  public ListIterator<T> listIterator(int index) {
    return new MyListIterator<>(index, this);
  }

  @Override
  @Nonnull
  public List<T> subList(int fromIndex, int toIndex) {
    throw new NotImplementedException("Not implemented yet");
  }

  @Nonnull
  @Override
  public Iterator<T> iterator() {
    return new MyIterator<>(this);
  }

  private void fastRemove(int index) {
    System.arraycopy(values, 0, values, 0, index);
    System.arraycopy(values, index + 1, values, index, values.length - index - 1);
    size--;
  }

  private class MyIterator<T> implements Iterator<T> {
    MyArrayList<T> list;
    int counter = 0;
    int lastElementIndex = -1;

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

  private class MyListIterator<T> extends MyIterator<T> implements ListIterator<T> {


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
}
