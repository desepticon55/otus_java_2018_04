package com.otus.homework10.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "person")
@Accessors(chain = true)
@Entity
@Table(name = "phone")
public class Phone {
  @Id
  @Column
  private Long id;
  @Column
  private String number;

  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;
}
