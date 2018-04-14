package com.otus.homework;

public class Pair<L, R> {
  private L left;
  private R right;

  public Pair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public static <L, R> Pair<L, R> of(L left, R right) {
    return new Pair<>(left, right);
  }

  public L getLeft() {
    return left;
  }

  public R getRight() {
    return right;
  }

  @Override
  public String toString() {
    return "Pair{" +
            "left=" + left +
            ", right=" + right +
            '}';
  }
}
