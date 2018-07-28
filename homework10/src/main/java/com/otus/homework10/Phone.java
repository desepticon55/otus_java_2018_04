package com.otus.homework10;

import com.otus.homework9.annotations.Column;
import com.otus.homework9.entity.DataSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "phone")
public class Phone extends DataSet {
  @Column
  private String number;
}
