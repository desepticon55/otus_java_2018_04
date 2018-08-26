package com.otus.homework12.entity;

import com.otus.homework12.annotations.Column;
import com.otus.homework12.annotations.Entity;
import com.otus.homework12.annotations.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@Entity(name = "employee")
public class Person extends DataSet {
  @Column
  private String name;
  @Column
  private Integer age;
  @OneToOne
  private Address address;
}
