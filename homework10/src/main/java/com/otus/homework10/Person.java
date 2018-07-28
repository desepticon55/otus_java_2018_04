package com.otus.homework10;

import com.otus.homework9.entity.DataSet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "person")
public class Person extends DataSet {
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
  private Integer age;
  @Column
  private String sex;
  @Column
  @OneToOne
  private Address address;
  @Column
  @ManyToOne
  private Phone phone;
}
