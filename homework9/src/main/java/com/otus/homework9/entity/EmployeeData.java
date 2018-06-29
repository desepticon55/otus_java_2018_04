package com.otus.homework9.entity;

import com.otus.homework9.annotations.Column;
import com.otus.homework9.annotations.Entity;
import com.otus.homework9.entity.DataSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@Entity(name = "Employee")
public class EmployeeData extends DataSet {
  @Column
  private String name;
  @Column
  private Integer age;
}
