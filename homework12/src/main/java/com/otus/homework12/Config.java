package com.otus.homework12;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class Config {

  @PostConstruct
  public void init() {
    ObjectFactory objectFactory = ObjectFactory.getInstance();
    objectFactory.init(this.getClass());
  }
}
