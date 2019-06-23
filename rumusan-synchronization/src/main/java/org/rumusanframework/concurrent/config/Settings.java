/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.config;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (10 Jun 2018)
 */
public final class Settings {

  public static final String CONFIG_LOCATION = "synchronize.config.location";

  public static final String DATASOURCE_DRIVER_CLASS = "synchronize.datasource.driver.class.name";

  public static final String DATASOURCE_URL = "synchronize.datasource.url";

  public static final String DATASOURCE_USERNAME = "synchronize.datasource.username";

  public static final String DATASOURCE_SECRET = "synchronize.datasource.password";

  public static final String LOG_CONFIG_LOCATION = "synchronize.log.config";

  private Settings() {
    // hide
  }
}