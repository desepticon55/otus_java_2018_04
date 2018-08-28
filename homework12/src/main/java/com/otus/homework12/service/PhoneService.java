package com.otus.homework12.service;

import com.otus.homework12.dao.PhoneDao;
import com.otus.homework12.entity.Phone;

import java.util.List;

public class PhoneService {
  private final PhoneDao phoneDao;

  public PhoneService() throws Exception {
    this.phoneDao = new PhoneDao();
  }

  public List<Phone> findAll() {
    return phoneDao.findAll();
  }

  public Boolean save(Phone phone) {
    return phoneDao.save(phone);
  }

  public Phone findById(Long id) {
    return phoneDao.findById(id);
  }
}
