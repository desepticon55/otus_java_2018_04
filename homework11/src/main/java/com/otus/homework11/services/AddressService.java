package com.otus.homework11.services;

import com.otus.homework11.annotations.Component;
import com.otus.homework11.annotations.InjectByType;
import com.otus.homework11.dao.AddressDao;
import com.otus.homework11.dao.PersonDao;
import com.otus.homework11.entity.Address;
import com.otus.homework11.entity.Person;

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
