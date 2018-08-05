package com.otus.homework11.entity;

import com.otus.homework11.annotations.Column;
import com.otus.homework11.annotations.Id;
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
