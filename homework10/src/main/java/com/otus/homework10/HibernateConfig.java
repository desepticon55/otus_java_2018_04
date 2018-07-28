package com.otus.homework10;

import com.otus.homework10.entity.Address;
import com.otus.homework10.entity.Person;
import com.otus.homework10.entity.Phone;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.Value;
import org.hibernate.cfg.Configuration;

@Component
@com.otus.homework9.annotations.Configuration
public class HibernateConfig {

  @Value("hibernate.dialect")
  private String hibernateDialect;
  @Value("hibernate.driver_class")
  private String hibernateDriver;
  @Value("hibernate.url")
  private String hibernateUrl;
  @Value("hibernate.username")
  private String hibernateUserName;
  @Value("hibernate.password")
  private String hibernatePassword;
  @Value("hibernate.show_sql")
  private String hibernateShowSql;
  @Value("hibernate.hbm2ddl")
  private String hibernateHbm2ddlAuto;
  @Value("hibernate.useSSL")
  private String hibernateUseSsl;
  @Value("hibernate.enable_lazy_load_no_trans")
  private String hibernateEnableLazyLoad;

  public Configuration getConfig() {
    Configuration configuration = new Configuration();

    //todo можно с помощью reflection найти все классы, аннотированные @Entity и добавить их в Configuration
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
