package com.otus.homework9;

public class EmptyEntityException extends RuntimeException {
  public EmptyEntityException() {
    super("Entity should be not empty");
  }
}
