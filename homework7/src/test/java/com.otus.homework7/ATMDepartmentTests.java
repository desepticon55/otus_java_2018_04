package com.otus.homework7;

import com.otus.homework6.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.RUBLE;
import static org.junit.Assert.*;

public class ATMDepartmentTests {

  private ATMFactory atmFactory;
  private Map<Integer, List<Banknote>> banknotesMap = new HashMap<>();

  @Before
  public void init() {
    atmFactory = new RubbleATMFactory();
    List<Banknote> banknotes = new ArrayList<>();
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(200).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(500).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(1000).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(2000).banknoteType(RUBLE).build());
    banknotesMap.put(100, filterBanknotesByNominal(banknotes, 100));
    banknotesMap.put(200, filterBanknotesByNominal(banknotes, 200));
    banknotesMap.put(500, filterBanknotesByNominal(banknotes, 500));
    banknotesMap.put(1000, filterBanknotesByNominal(banknotes, 1000));
    banknotesMap.put(2000, filterBanknotesByNominal(banknotes, 2000));
  }

  @Test
  public void addATMToDepartment() {
    ATMDepartment department = new FirstBankATMDepartment();
    ATM atm = atmFactory.createATM();
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(5000)
            .build());
    assertTrue(department.addATM(atm));
  }

  @Test
  public void getATMListFromDepartmentTest() {
    ATMDepartment department = new FirstBankATMDepartment();
    ATM atm = atmFactory.createATM();
    addBanknoteToATM(atm, 1000);
    addBanknoteToATM(atm, 2000);
    department.addATM(atm);
    List<ATM> atmList = department.getATMList();
    assertFalse(atmList.isEmpty());
    assertEquals(atm, atmList.get(0));
  }

  @Test
  public void getSumTotalBalanceAllATMFromDepartment() {
    ATMDepartment department = new FirstBankATMDepartment();
    ATM atm = atmFactory.createATM();
    addBanknoteToATM(atm, 1000);
    addBanknoteToATM(atm, 2000);
    department.addATM(atm);
    atm = atmFactory.createATM();
    addBanknoteToATM(atm, 100);
    addBanknoteToATM(atm, 200);
    addBanknoteToATM(atm, 500);
    department.addATM(atm);
    atm = atmFactory.createATM();
    addBanknoteToATM(atm, 5000);
    addBanknoteToATM(atm, 5000);
    addBanknoteToATM(atm, 5000);
    department.addATM(atm);
    Double totalBalance = department.getTotalBalanceAllATM();
    assertNotNull(totalBalance);
    assertEquals(18800, totalBalance, 0);
  }

  @Test
  public void initATM() {
    ATMDepartment department = new FirstBankATMDepartment();
    ATM atm = atmFactory.createATM(banknotesMap);
    department.addATM(atm);
    atm = department.getATMList().get(0);
    atm.withdrawCash(2700);
    assertEquals(1100, atm.getCashBalance());
    department.initializeAllAtm();
    assertEquals(3800, atm.getCashBalance());
  }

  private void addBanknoteToATM(ATM atm, Integer nominal) {
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(nominal)
            .build());
  }

  private List<Banknote> filterBanknotesByNominal(List<Banknote> banknotes, Integer nominal) {
    return banknotes.stream()
            .filter(el -> el.getBanknoteNominal().equals(nominal))
            .collect(Collectors.toList());
  }
}
