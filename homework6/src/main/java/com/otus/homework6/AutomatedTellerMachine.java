package com.otus.homework6;

import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AutomatedTellerMachine {
  List<Banknote> withdrawCash(Integer sumCash);

  Integer getCashBalance();

  void addBanknote(Banknote banknote);

  @SneakyThrows
  default List<Integer> getNominalListFromProperties(String banknoteNominalPropertyKey) {
    Properties properties = new Properties();
    properties.load(getClass().getResourceAsStream("/application.properties"));
    String s = properties.getProperty(banknoteNominalPropertyKey);
    if (Objects.nonNull(s) && !s.isEmpty()) {
      return Stream.of(s.split(","))
              .map(String::trim)
              .map(Integer::parseInt)
              .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

}
