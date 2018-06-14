/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Jun 2018)
 *
 */
public class DataSourceFactory {
	private static final int maxPoolSize = 50;
	private final Log logger = LogFactory.getLog(getClass());

	public DataSource getDataSource(DataSourceContext context) {
		if (logger.isDebugEnabled()) {
			logger.debug("DataSource context: " + context.toString());
		}

		return new GenericPoolDataSourceLoader().getDataSource(context);
	}

	interface DataSourceLoader {
		public DataSource getDataSource(DataSourceContext context);
	}

	class GenericPoolDataSourceLoader implements DataSourceLoader {
		@Override
		public DataSource getDataSource(DataSourceContext context) {
			BasicDataSource bds = new BasicDataSource();

			bds.setDriverClassName(context.getDriverClassName());
			bds.setUrl(context.getUrl());
			bds.setUsername(context.getUsername());
			bds.setPassword(context.getPassword());
			bds.setInitialSize(maxPoolSize);
			bds.setMaxTotal(maxPoolSize);

			return bds;
		}
	}
}