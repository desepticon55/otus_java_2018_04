package com.otus.homework6;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import static com.otus.homework6.BanknoteType.RUBLE;

public class RubleATM implements ATM {

  private static final String BANKNOTE_NOMINAL_PROPERTY = "banknote.nominal.ruble";

  private NominalListPropertiesReader propertiesReader;
  @Getter
  private Map<Integer, List<Banknote>> banknoteMap;
  private Map<Integer, List<Banknote>> initBanknoteMap;
  private List<Integer> nominalList;

  public RubleATM() {
    this.banknoteMap = new HashMap<>();
    this.initBanknoteMap = new HashMap<>();
    this.propertiesReader = new NominalListPropertiesReader();
    this.nominalList = propertiesReader.getNominalListFromProperties(BANKNOTE_NOMINAL_PROPERTY);
  }


  public RubleATM(Map<Integer, List<Banknote>> banknoteMap) {
    this.banknoteMap = banknoteMap;
    this.initBanknoteMap = copy(banknoteMap);
    this.propertiesReader = new NominalListPropertiesReader();
    this.nominalList = propertiesReader.getNominalListFromProperties(BANKNOTE_NOMINAL_PROPERTY);
  }

  @Override
  public List<Banknote> withdrawCash(Integer sumCash) {
    Objects.requireNonNull(sumCash, "Sum should be not null");
    int balance = getCashBalance();
    if (sumCash > balance) {
      throw new EmptyATMException(sumCash);
    }

    if (sumCash % 100 > 0) {
      throw new IllegalArgumentException("Number must be a multiple of 100");
    }

    List<Banknote> result = new ArrayList<>();
    calculateBanknoteCount(result, sumCash);
    return result;
  }

  @Override
  public int getCashBalance() {
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
      banknotesByNominal = Lists.newArrayList(banknote);
    } else {
      banknotesByNominal.add(banknote);
    }
    banknoteMap.put(banknote.getBanknoteNominal(), banknotesByNominal);
  }

  @Override
  public void initializeATM(Map<Integer, List<Banknote>> map) {
    if (Objects.isNull(map) || map.isEmpty()) {
      banknoteMap = initBanknoteMap;
    } else {
      banknoteMap = map;
    }
  }

  @Override
  public void initializeATM() {
    initializeATM(null);
  }

  private void calculateBanknoteCount(List<Banknote> banknoteList, Integer sumCash) {
    Integer sum = sumCash;
    while (sum > 0) {
      Map<Integer, List<Banknote>> bMap = getFilteredBanknoteMap();
      Integer minBanknote = getMinBanknoteNominal(bMap, sum);

      int sumBanknotesLessOrEqualNominal = getSumBanknoteLessOrEqualNominal(bMap, minBanknote);

      if (Objects.isNull(minBanknote) || sum > sumBanknotesLessOrEqualNominal) {
        throw new EmptyATMException("Not enough banknotes of the desired denomination");
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

  private HashMap<Integer, List<Banknote>> copy(Map<Integer, List<Banknote>> original) {
    HashMap<Integer, List<Banknote>> copy = new HashMap<>();
    original.forEach((key, value) -> copy.put(key, new ArrayList<>(value)));
    return copy;
  }
}
