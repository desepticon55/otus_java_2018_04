package com.otus.homework13;

import com.otus.homework13.entity.Address;
import com.otus.homework13.entity.Person;
import com.otus.homework13.entity.Phone;
import org.hibernate.cfg.Configuration;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HibernateConfig {

  @Value("${hibernate.dialect}")
  private String hibernateDialect;
  @Value("${hibernate.driver_class}")
  private String hibernateDriver;
  @Value("${hibernate.url}")
  private String hibernateUrl;
  @Value("${hibernate.username}")
  private String hibernateUserName;
  @Value("${hibernate.password}")
  private String hibernatePassword;
  @Value("${hibernate.show_sql}")
  private String hibernateShowSql;
  @Value("${hibernate.hbm2ddl}")
  private String hibernateHbm2ddlAuto;
  @Value("${hibernate.useSSL}")
  private String hibernateUseSsl;
  @Value("${hibernate.enable_lazy_load_no_trans}")
  private String hibernateEnableLazyLoad;

  public Configuration getConfig() {
    Configuration configuration = new Configuration();

    configuration.addAnnotatedClass(Person.class);
    configuration.addAnnotatedClass(Address.class);
    configuration.addAnnotatedClass(Phone.class);

    configuration.setProperty("hibernate.dialect", hibernateDialect);
    configuration.setProperty("hibernate.connection.driver_class", hibernateDriver);
    configuration.setProperty("hibernate.connection.url", hibernateUrl);
    configuration.setProperty("hibernate.connection.username", hibernateUserName);
    configuration.setProperty("hibernate.connection.password", hibernatePassword);
    configuration.setProperty("hibernate.show_sql", hibernateShowSql);
    configuration.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
    configuration.setProperty("hibernate.connection.useSSL", hibernateUseSsl);
    configuration.setProperty("hibernate.enable_lazy_load_no_trans", hibernateEnableLazyLoad);
    return configuration;
  }
}
