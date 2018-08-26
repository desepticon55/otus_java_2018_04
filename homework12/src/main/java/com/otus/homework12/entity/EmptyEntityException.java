package com.otus.homework12.entity;

public class EmptyEntityException extends RuntimeException {
  public EmptyEntityException() {
    super("Entity should be not empty");
  }
}
