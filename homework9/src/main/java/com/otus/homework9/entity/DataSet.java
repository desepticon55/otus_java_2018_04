package com.otus.homework9.entity;

import com.otus.homework9.annotations.Column;
import com.otus.homework9.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class DataSet {
  @Id
  @Column
  private Long id;
}
