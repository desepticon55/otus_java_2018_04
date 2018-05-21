package com.otus.homework6;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Banknote {
  private Integer banknoteNominal;
  private BanknoteType banknoteType;
}
