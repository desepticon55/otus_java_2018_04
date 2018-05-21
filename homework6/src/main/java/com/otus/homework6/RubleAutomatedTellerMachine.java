package com.otus.homework6;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.RUBLE;

public class RubleAutomatedTellerMachine implements AutomatedTellerMachine {

  private static final String BANKNOTE_NOMINAL_PROPERTY = "banknote.nominal.ruble";

  @Getter
  private Map<Integer, List<Banknote>> banknotes;

  private List<Integer> nominalList;

  public RubleAutomatedTellerMachine() {
    banknotes = new HashMap<>();
    nominalList = getNominalListFromProperties(BANKNOTE_NOMINAL_PROPERTY);
  }

  @Override
  public List<Banknote> withdrawCash(Integer sumCash) {
    Objects.requireNonNull(sumCash, "Sum should be not null");
    if (sumCash > getCashBalance()) {
      throw new EmptyATMException("ATM does not have enough money");
    }

    if (sumCash % 100 > 0) {
      throw new IllegalArgumentException("Number must be a multiple of 100");
    }

    List<Integer> filteredNominalList = nominalList.stream()
            .filter(el -> el <= sumCash)
            .collect(Collectors.toList());


    return null;
  }

  @Override
  public Integer getCashBalance() {
    return banknotes.entrySet().stream()
            .mapToInt(el -> el.getValue().stream().mapToInt(Banknote::getBanknoteNominal).sum())
            .sum();
  }

  @Override
  public void addBanknote(Banknote banknote) {
    Objects.requireNonNull(banknote, "Banknote should be not null");
    if (!banknote.getBanknoteType().equals(RUBLE)) {
      throw new IllegalArgumentException("ATM accepts only ruble banknotes");
    }

    if (!nominalList.contains(banknote.getBanknoteNominal())) {
      throw new IllegalArgumentException("Such a banknote does not exist. Check the configuration");
    }

    List<Banknote> banknotesByNominal = banknotes.get(banknote.getBanknoteNominal());
    if (Objects.isNull(banknotesByNominal)) {
      banknotesByNominal = Collections.singletonList(banknote);
    } else {
      banknotesByNominal.add(banknote);
    }
    banknotes.put(banknote.getBanknoteNominal(), banknotesByNominal);
  }
}
