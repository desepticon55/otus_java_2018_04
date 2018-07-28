package com.otus.homework10.entity;

import com.otus.homework9.annotations.Column;
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
@Table(name = "phone")
public class Phone extends DataSet {
  @Column
  private String number;

  @ManyToOne
  @JoinColumn(name = "person_id", nullable = false)
  private Person person;
}
