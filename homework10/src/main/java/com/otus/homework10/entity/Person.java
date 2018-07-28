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
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "person_id"))
})
public class Person extends DataSet {
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

  @OneToMany(mappedBy="person", cascade = CascadeType.PERSIST)
  private List<Phone> phones;
}

