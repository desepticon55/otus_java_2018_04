package com.otus.homework7;

import com.otus.homework6.ATM;
import com.otus.homework6.Banknote;

import java.util.List;
import java.util.Map;

public interface ATMDepartment {
  List<ATM> getATMList();

  boolean addATM(ATM atm);

  Double getTotalBalanceAllATM();

  void initializeAllAtm();

  void initializeAtm(int index);

  void initializeAtm(int index, Map<Integer, List<Banknote>> map);
}
