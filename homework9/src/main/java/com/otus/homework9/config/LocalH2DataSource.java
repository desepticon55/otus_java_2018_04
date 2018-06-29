package com.otus.homework9.config;

import com.otus.homework9.annotations.Configuration;
import com.otus.homework9.annotations.Value;
import lombok.SneakyThrows;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Configuration
public class LocalH2DataSource implements DataSource {

  @Value("h2.local.login")
  private String login;
  @Value("h2.local.password")
  private String password;
  @Value("h2.local.url")
  private String url;
  @Value("h2.local.className")
  private String className;

  @Override
  public Connection getConnection() throws SQLException {
    return getConnection(login, password);
  }

  @Override
  @SneakyThrows
  public Connection getConnection(String username, String password) throws SQLException {
    Class.forName(className).newInstance();
    return DriverManager.getConnection(url, username, password);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new NotImplementedException();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new NotImplementedException();
  }
}
