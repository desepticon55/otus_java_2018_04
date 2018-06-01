package com.otus.homework7;

import com.otus.homework6.ATM;
import com.otus.homework6.Banknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirstBankATMDepartment implements ATMDepartment {

  private List<ATM> atmList;

  public FirstBankATMDepartment() {
    atmList = new ArrayList<>();
  }

  @Override
  public List<ATM> getATMList() {
    return atmList;
  }

  @Override
  public boolean addATM(ATM atm) {
    return atmList.add(Objects.requireNonNull(atm, "Object ATM should not be NULL"));
  }

  @Override
  public Double getTotalBalanceAllATM() {
    return atmList.stream()
            .mapToDouble(ATM::getCashBalance)
            .sum();
  }

  @Override
  public void initializeAllAtm() {
    atmList.forEach(ATM::initializeATM);
  }

  @Override
  public void initializeAtm(int index) {
    atmList.get(index).initializeATM();
  }

  @Override
  public void initializeAtm(int index, Map<Integer, List<Banknote>> map) {
    atmList.get(index).initializeATM(map);
  }
}
