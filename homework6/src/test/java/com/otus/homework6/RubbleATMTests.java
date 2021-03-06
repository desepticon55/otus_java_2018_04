package com.otus.homework6;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.testng.collections.Lists;

import java.util.*;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.DOLLAR;
import static com.otus.homework6.BanknoteType.RUBLE;
import static org.junit.Assert.*;

public class RubbleATMTests {

  private Map<Integer, List<Banknote>> banknotesMap = new HashMap<>();
  private ATMFactory atmFactory;

  @Before
  public void init() {
    atmFactory = new RubbleATMFactory();
    List<Banknote> banknotes = new ArrayList<>();
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(100).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(500).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(500).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(1000).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(1000).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(1000).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(2000).banknoteType(RUBLE).build());
    banknotes.add(Banknote.builder().banknoteNominal(5000).banknoteType(RUBLE).build());
    banknotesMap.put(100, filterBanknotesByNominal(banknotes, 100));
    banknotesMap.put(500, filterBanknotesByNominal(banknotes, 500));
    banknotesMap.put(1000, filterBanknotesByNominal(banknotes, 1000));
    banknotesMap.put(2000, filterBanknotesByNominal(banknotes, 2000));
    banknotesMap.put(5000, filterBanknotesByNominal(banknotes, 5000));
  }

  @Test
  @SneakyThrows
  public void getCashBalanceFromRubleATMTest() {
    ATM atm = atmFactory.createATM(banknotesMap);
    Integer balance = atm.getCashBalance();
    assertEquals(11600L, balance.longValue());
  }

  @Test
  public void addBanknoteToRubbleATMTest() {
    ATM atm = atmFactory.createATM();
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(5000)
            .build());
    assertEquals(5000L, atm.getCashBalance());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDollarBanknoteTest() {
    ATM atm = atmFactory.createATM();
    atm.addBanknote(Banknote.builder()
            .banknoteType(DOLLAR)
            .banknoteNominal(5000)
            .build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addUnknownBanknoteTest() {
    ATM atm = atmFactory.createATM();
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(50)
            .build());
  }

  @Test
  @SneakyThrows
  public void withdrawCashFromRubbleATMTest() {
    ATM atm = atmFactory.createATM(banknotesMap);
    List<Banknote> cash = atm.withdrawCash(2300);
    assertNotNull(cash);
    assertFalse(cash.isEmpty());
    assertEquals(Lists.newArrayList(2000, 100, 100, 100), mapListBanknoteToNominalList(cash));
    cash = atm.withdrawCash(1700);
    assertNotNull(cash);
    assertFalse(cash.isEmpty());
    assertEquals(Lists.newArrayList(1000, 500, 100, 100), mapListBanknoteToNominalList(cash));
    cash = atm.withdrawCash(1000);
    assertNotNull(cash);
    assertFalse(cash.isEmpty());
    assertEquals(Collections.singletonList(1000), mapListBanknoteToNominalList(cash));
    cash = atm.withdrawCash(6600);
    assertNotNull(cash);
    assertFalse(cash.isEmpty());
    assertEquals(Lists.newArrayList(5000, 1000, 500, 100), mapListBanknoteToNominalList(cash));
  }

  @Test(expected = EmptyATMException.class)
  @SneakyThrows
  public void withdrawCashErrorTest() {
    ATM atm = atmFactory.createATM();
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(2000)
            .build());
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(500)
            .build());
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(100)
            .build());
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(100)
            .build());
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(100)
            .build());
    List<Banknote> cash = atm.withdrawCash(2900);
  }

  private List<Integer> mapListBanknoteToNominalList(List<Banknote> cash) {
    return cash.stream()
            .map(Banknote::getBanknoteNominal)
            .collect(Collectors.toList());
  }

  private List<Banknote> filterBanknotesByNominal(List<Banknote> banknotes, Integer nominal) {
    return banknotes.stream()
            .filter(el -> el.getBanknoteNominal().equals(nominal))
            .collect(Collectors.toList());
  }
}
