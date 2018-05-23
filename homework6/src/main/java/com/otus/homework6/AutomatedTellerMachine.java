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

  int getCashBalance();

  void addBanknote(Banknote banknote);

}
