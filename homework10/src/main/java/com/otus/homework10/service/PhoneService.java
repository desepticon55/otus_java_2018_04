package com.otus.homework10.service;

import com.otus.homework10.dao.PhoneDao;
import com.otus.homework10.entity.Phone;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InjectByType;

import java.util.List;

@Component
public class PhoneService {
  @InjectByType
  private PhoneDao phoneDao;

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
