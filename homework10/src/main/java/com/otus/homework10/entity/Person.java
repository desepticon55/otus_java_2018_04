package com.otus.homework10.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "person")
@Accessors(chain = true)
public class Person {

  @Id
  @Column(name = "person_id")
  private Long id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
  private Integer age;
  @Column
  private String sex;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @OneToMany(mappedBy="person", cascade = CascadeType.ALL)
  private List<Phone> phones;
}

