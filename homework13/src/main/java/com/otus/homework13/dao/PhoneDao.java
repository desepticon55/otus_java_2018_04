package com.otus.homework13.dao;

import com.otus.homework13.DBServiceHibernate;
import com.otus.homework13.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhoneDao {
  @Autowired
  private DBServiceHibernate dbService;

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
