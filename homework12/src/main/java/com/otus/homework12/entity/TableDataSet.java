package com.otus.homework12.entity;

import com.otus.homework12.annotations.Column;
import com.otus.homework12.annotations.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "Table")
public class TableDataSet extends DataSet {
  @Column
  private Double width;
  @Column
  private Double height;
  @Column
  private String color;
}
