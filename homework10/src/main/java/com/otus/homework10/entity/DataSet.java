package com.otus.homework10.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DataSet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
