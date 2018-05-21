package com.otus.homework6;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.*;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.RUBLE;
import static org.junit.Assert.*;

public class ATMTests {

  private Map<Integer, List<Banknote>> banknotesMap = new HashMap<>();

  @Before
  public void init() {
    List<Banknote> banknotes = new ArrayList<>();
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
    AutomatedTellerMachine atm = new RubleAutomatedTellerMachine();
    MemberModifier
            .field(RubleAutomatedTellerMachine.class, "banknotes")
            .set(atm, banknotesMap);
    Integer balance = atm.getCashBalance();
    assertTrue(Objects.nonNull(balance));
    assertEquals(11500L, balance.longValue());
  }

  @Test
  public void addBanknoteToRubbleATMTest() {
    AutomatedTellerMachine atm = new RubleAutomatedTellerMachine();
    atm.addBanknote(Banknote.builder()
            .banknoteType(RUBLE)
            .banknoteNominal(5000)
            .build());
    assertEquals(5000L, atm.getCashBalance().longValue());
  }

  @Test
  @SneakyThrows
  public void withdrawCashFromRubbleATMTest() {
    AutomatedTellerMachine atm = new RubleAutomatedTellerMachine();
    MemberModifier
            .field(RubleAutomatedTellerMachine.class, "banknotes")
            .set(atm, banknotesMap);
    List<Banknote> cash = atm.withdrawCash(500);
    assertNotNull(cash);
    assertFalse(cash.isEmpty());
  }

  private List<Banknote> filterBanknotesByNominal(List<Banknote> banknotes, Integer nominal) {
    return banknotes.stream()
            .filter(el -> el.getBanknoteNominal().equals(nominal))
            .collect(Collectors.toList());
  }
}
