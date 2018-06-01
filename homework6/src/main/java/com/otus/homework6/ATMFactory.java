package com.otus.homework6;

import java.util.List;
import java.util.Map;

public abstract class ATMFactory {

  public abstract ATM createATM(Map<Integer, List<Banknote>> banknoteMap);

  public abstract ATM createATM();

}
