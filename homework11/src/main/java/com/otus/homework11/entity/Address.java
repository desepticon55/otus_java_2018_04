package com.otus.homework11.entity;

import com.otus.homework11.annotations.Column;
import com.otus.homework11.annotations.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Entity(name = "address")
@Accessors(chain = true)
public class Address extends DataSet {
  @Column
  private String street;
  @Column
  private String city;
}
