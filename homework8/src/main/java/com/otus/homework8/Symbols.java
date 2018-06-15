package com.otus.homework8;

public enum Symbols {
  NULL("null"),
  COMMA(","),
  DOUBLE_APOSTROPHE("\""),
  SQUARE_BRACKET("["),
  CLOSING_SQUARE_BRACKET("]"),
  BRACE("{"),
  CLOSING_BRACE("}"),
  BINARY(":");
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
