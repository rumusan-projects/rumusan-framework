/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Jun 2018)
 */
public class DataSourceFactory {

  private static final int MAX_POOL_SIZE = 50;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public DataSource getDataSource(DataSourceContext context) {
    if (logger.isDebugEnabled()) {
      logger.debug("DataSource context: " + context.toString());
    }

    return new GenericPoolDataSourceLoader().getDataSource(context);
  }

  interface DataSourceLoader {

    DataSource getDataSource(DataSourceContext context);
  }

  class GenericPoolDataSourceLoader implements DataSourceLoader {

    @Override
    public DataSource getDataSource(DataSourceContext context) {
      BasicDataSource bds = new BasicDataSource();

      bds.setDriverClassName(context.getDriverClassName());
      bds.setUrl(context.getUrl());
      bds.setUsername(context.getUsername());
      bds.setPassword(context.getPassword());
      bds.setInitialSize(MAX_POOL_SIZE);
      bds.setMaxTotal(MAX_POOL_SIZE);

      return bds;
    }
  }
}