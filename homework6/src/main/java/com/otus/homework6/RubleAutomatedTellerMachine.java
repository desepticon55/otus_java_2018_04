package com.otus.homework6;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.RUBLE;

public class RubleAutomatedTellerMachine implements AutomatedTellerMachine {

  private static final String BANKNOTE_NOMINAL_PROPERTY = "banknote.nominal.ruble";

  @Getter
  private Map<Integer, List<Banknote>> banknoteMap;

  private List<Integer> nominalList;

  public RubleAutomatedTellerMachine() {
    banknoteMap = new HashMap<>();
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

    List<Banknote> result = new ArrayList<>();
    calculateBanknoteCount(result, sumCash);
    return result;
  }

  @Override
  public Integer getCashBalance() {
    return banknoteMap.entrySet().stream()
            .mapToInt(el -> el.getValue().stream().mapToInt(Banknote::getBanknoteNominal).sum())
            .sum();
  }

  @Override
  public void addBanknote(Banknote banknote) {
    Objects.requireNonNull(banknote, "Banknote should be not null");
    if (!banknote.getBanknoteType().equals(RUBLE)) {
      throw new IllegalArgumentException("ATM accepts only ruble banknoteMap");
    }

    if (!nominalList.contains(banknote.getBanknoteNominal())) {
      throw new IllegalArgumentException("Such a banknote does not exist. Check the configuration");
    }

    List<Banknote> banknotesByNominal = banknoteMap.get(banknote.getBanknoteNominal());
    if (Objects.isNull(banknotesByNominal)) {
      banknotesByNominal = Collections.singletonList(banknote);
    } else {
      banknotesByNominal.add(banknote);
    }
    banknoteMap.put(banknote.getBanknoteNominal(), banknotesByNominal);
  }


  private void calculateBanknoteCount(List<Banknote> banknoteList, Integer sumCash) {
    Integer sum = sumCash;
    while (sum > 0) {
      Map<Integer, List<Banknote>> bMap = getFilteredBanknoteMap();
      Integer minBanknote = getMinBanknoteNominal(bMap, sum);

      if (Objects.isNull(minBanknote) || sum > getSumBanknoteLessOrEqualNominal(bMap, minBanknote)) {
        throw new EmptyATMException("ATM does not have enough money");
      }

      List<Banknote> b = bMap.get(minBanknote);
      assert !b.isEmpty();
      Banknote banknote = b.remove(0);
      bMap.put(minBanknote, b);
      banknoteList.add(banknote);
      sum -= minBanknote;
    }
    banknoteMap = getFilteredBanknoteMap();
  }

  private Integer getMinBanknoteNominal(Map<Integer, List<Banknote>> bMap, Integer sumCash) {
    return bMap.keySet().stream()
            .filter(el -> el <= sumCash)
            .collect(Collectors.toMap(x -> x, x -> sumCash / x))
            .entrySet().stream()
            .min(Map.Entry.comparingByValue(Integer::compareTo))
            .map(Map.Entry::getKey)
            .orElse(null);
  }

  private Integer getSumBanknoteLessOrEqualNominal(Map<Integer, List<Banknote>> banknoteMap, Integer nominal) {
    return banknoteMap.entrySet().stream()
            .filter(el -> el.getKey() <= nominal)
            .map(Map.Entry::getValue)
            .mapToInt(el -> el.stream().mapToInt(Banknote::getBanknoteNominal).sum())
            .sum();
  }

  private Map<Integer, List<Banknote>> getFilteredBanknoteMap() {
    return banknoteMap.entrySet().stream()
            .filter(el -> !el.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
