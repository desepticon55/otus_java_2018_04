package com.otus.homework12.dao;

import com.otus.homework12.DBServiceHibernateImpl;
import com.otus.homework12.entity.Phone;

import java.util.List;

public class PhoneDao {
  private DBServiceHibernateImpl dbService;

  public PhoneDao() throws Exception {
    this.dbService = new DBServiceHibernateImpl();
  }

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
