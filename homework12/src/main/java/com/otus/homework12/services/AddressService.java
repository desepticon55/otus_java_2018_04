package com.otus.homework12.services;

import com.otus.homework12.annotations.Component;
import com.otus.homework12.annotations.InjectByType;
import com.otus.homework12.dao.AddressDao;
import com.otus.homework12.entity.Address;

import java.util.List;

@Component
public class AddressService {
  @InjectByType
  private AddressDao addressDao;

  public List<Address> findAll() {
    return addressDao.findAll();
  }

  public Boolean save(Address address) {
    return addressDao.save(address);
  }

  public Address findById(Long id) {
    return addressDao.findById(id);
  }
}
