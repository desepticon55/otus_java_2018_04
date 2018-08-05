package com.otus.homework11.test;

import com.otus.homework11.cache.CacheEngine;
import com.otus.homework11.cache.CacheEngineImpl;
import com.otus.homework11.cache.CacheEntry;
import com.otus.homework11.entity.Person;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CacheEngineTests {

  @Test
  public void putOrGetOnCaheTest() throws Exception {
    CacheEngine<Integer, Person> employeeDataCacheEngine =
            new CacheEngineImpl<>(1, 5000, 0);
    assertNotNull(employeeDataCacheEngine);
    employeeDataCacheEngine.put(1, DataFactory.createEmployee(1L));
    Person person = employeeDataCacheEngine.get(1);
    assertNotNull(person);
    assertEquals(1, employeeDataCacheEngine.getHitCount());
    assertEquals(0, employeeDataCacheEngine.getMissCount());
    person = employeeDataCacheEngine.get(2);
    assertNull(person);
    assertEquals(1, employeeDataCacheEngine.getHitCount());
    assertEquals(1, employeeDataCacheEngine.getMissCount());
    TimeUnit.SECONDS.sleep(6);
    person = employeeDataCacheEngine.get(1);
    assertNull(person);
    assertEquals(1, employeeDataCacheEngine.getHitCount());
    assertEquals(2, employeeDataCacheEngine.getMissCount());
  }
}
