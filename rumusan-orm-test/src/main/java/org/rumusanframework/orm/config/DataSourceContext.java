/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.config;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Jun 2018)
 */
public class DataSourceContext {

  private String driverClassName;

  private String url;

  private String username;

  private String password;

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DataSourceContext [driverClassName=");
    builder.append(driverClassName);
    builder.append(", url=");
    builder.append(url);
    builder.append(", username=");
    builder.append(username);
    builder.append(", password=");
    builder.append(password);
    builder.append("]");
    return builder.toString();
  }
}