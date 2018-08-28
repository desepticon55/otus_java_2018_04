package com.otus.homework12.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class DataSet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
