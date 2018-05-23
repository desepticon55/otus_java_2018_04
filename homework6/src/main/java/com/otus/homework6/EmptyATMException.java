package com.otus.homework6;

public class EmptyATMException extends RuntimeException {
  Integer balance;

  public EmptyATMException(String message) {
    super(message);
  }

  public EmptyATMException(Integer balance) {
    super(String.format("ATM does not have enough money. Current balance = %d", balance));
  }
}
