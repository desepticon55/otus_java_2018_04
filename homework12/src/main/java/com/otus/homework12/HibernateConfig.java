package com.otus.homework12;

import com.otus.homework12.annotations.Value;
import com.otus.homework12.entity.Address;
import com.otus.homework12.entity.Person;
import com.otus.homework12.entity.Phone;
import lombok.SneakyThrows;
import org.hibernate.cfg.Configuration;

import java.util.Properties;
import java.util.stream.Stream;

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

  public Configuration getConfig() throws Exception {
    configureFields();
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

  private void configureFields() throws Exception {
    Properties properties = new Properties();
    properties.load(getClass().getResourceAsStream("/application.properties"));
    Stream.of(this.getClass().getDeclaredFields())
            .filter(f -> f.isAnnotationPresent(Value.class))
            .forEach(f -> {
              try {
                f.setAccessible(true);
                Value annotation = f.getAnnotation(Value.class);
                String propertyKey = annotation.value();
                f.set(this, properties.getProperty(propertyKey));
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
  }
}
