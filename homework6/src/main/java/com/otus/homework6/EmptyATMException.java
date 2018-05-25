package com.otus.homework6;

public class EmptyATMException extends RuntimeException {

  public EmptyATMException(String message) {
    super(message);
  }

  public EmptyATMException(Integer amountRequested) {
    super(String.format("ATM does not have enough money. Amount requested = %d", amountRequested));
  }
}
