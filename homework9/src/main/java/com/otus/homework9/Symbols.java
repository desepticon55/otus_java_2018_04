package com.otus.homework9;

public enum Symbols {
  COMMA(","),
  CIRCLE_LEFT_BRACKET("("),
  CIRCLE_RIGHT_BRACKET(")");
  String value;

  Symbols(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getValue();
  }
}