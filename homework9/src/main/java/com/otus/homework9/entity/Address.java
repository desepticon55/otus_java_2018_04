package com.otus.homework9.entity;

import com.otus.homework9.annotations.Column;
import com.otus.homework9.annotations.Entity;
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
