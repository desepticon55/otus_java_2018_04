package com.otus.homework6;

import java.util.List;
import java.util.Map;

public class RubbleATMFactory extends ATMFactory {
  @Override
  public ATM createATM(Map<Integer, List<Banknote>> banknoteMap) {
    return new RubleATM(banknoteMap);
  }

  @Override
  public ATM createATM() {
    return new RubleATM();
  }
}
