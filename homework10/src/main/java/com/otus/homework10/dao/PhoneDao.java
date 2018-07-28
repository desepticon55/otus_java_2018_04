package com.otus.homework10.dao;

import com.otus.homework10.DBServiceHibernateImpl;
import com.otus.homework10.entity.Phone;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InjectByType;

import java.util.List;

@Component
public class PhoneDao {
  @InjectByType
  private DBServiceHibernateImpl dbService;

  public List<Phone> findAll() {
    return dbService.findAll(Phone.class);
  }

  public Boolean save(Phone phone) {
    try {
      dbService.save(phone);
      return Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      return Boolean.FALSE;
    }
  }

  public Phone findById(Long id) {
    return dbService.findById(id, Phone.class);
  }
}
