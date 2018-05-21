package com.otus.homework6;

/**
 * Объект класса АТМ должен уметь
 * • принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
 * • выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
 * • выдавать сумму остатка денежных средств
 */

public class Application {
  public static void main(String[] args) {
    AutomatedTellerMachine rubleATM = new RubleAutomatedTellerMachine();
    rubleATM.getCashBalance();
  }
}
