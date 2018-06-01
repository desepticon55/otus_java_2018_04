package com.otus.homework6;

import java.util.List;
import java.util.Map;

public interface ATM {
  List<Banknote> withdrawCash(Integer sumCash);

  int getCashBalance();

  void addBanknote(Banknote banknote);

  void initializeATM();

  void initializeATM(Map<Integer, List<Banknote>> map);
}
