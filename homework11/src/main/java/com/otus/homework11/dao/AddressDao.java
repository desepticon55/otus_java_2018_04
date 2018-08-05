package com.otus.homework11.dao;

import com.otus.homework11.annotations.Component;
import com.otus.homework11.annotations.InitMethod;
import com.otus.homework11.annotations.InjectByType;
import com.otus.homework11.cache.CacheEngine;
import com.otus.homework11.entity.Address;
import com.otus.homework11.entity.Person;
import com.otus.homework11.services.CacheManager;
import com.otus.homework11.services.DBService;

import java.util.List;
import java.util.Objects;

@Component
public class AddressDao {

  @InjectByType
  private DBService dbService;
  @InjectByType
  private CacheManager cacheManager;
  private CacheEngine<Long, Address> addressCacheEngine;

  @InitMethod
  public void init() {
    addressCacheEngine = cacheManager.createCache(500, 0, 1000000);
  }

  public List<Address> findAll() {
    return dbService.findAll(Address.class);
  }

  public Boolean save(Address phone) {
    try {
      dbService.save(phone);
      return Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      return Boolean.FALSE;
    }
  }

  public Address findById(Long id) {
    Address address = addressCacheEngine.get(id);
    if (Objects.isNull(address)) {
      address = dbService.findById(id, Address.class);
      addressCacheEngine.put(id, address);
    }
    return address;
  }
}
