package com.otus.homework11.entity;

public class EmptyEntityException extends RuntimeException {
  public EmptyEntityException() {
    super("Entity should be not empty");
  }
}
