/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oracle.ucp.jdbc.PoolDataSourceImpl;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Jun 2018)
 *
 */
public class DataSourceFactory {
	private static final int maxPoolSize = 50;
	private static final Log logger = LogFactory.getLog(DataSourceFactory.class);

	private DataSourceFactory() {
		//
	}

	public static DataSource getDataSource(DataSourceContext context) {
		if (logger.isDebugEnabled()) {
			logger.debug("DataSource context: " + context.toString());
		}

		String driverClassName = context.getDriverClassName().toLowerCase();

		if (driverClassName.contains("oracle")) {
			return new OraclePoolDataSource().getDataSource(context);
		} else {
			return new GenericPoolDataSource().getDataSource(context);
		}
	}

	interface DataSourceLoader {
		public DataSource getDataSource(DataSourceContext context);
	}

	static class OraclePoolDataSource implements DataSourceLoader {
		@Override
		public DataSource getDataSource(DataSourceContext context) {
			PoolDataSourceImpl ods = new PoolDataSourceImpl();

			try {
				ods.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
				ods.setURL(context.getUrl());
				ods.setUser(context.getUsername());
				ods.setPassword(context.getPassword());
				ods.setMaxPoolSize(maxPoolSize);
				ods.setMaxConnectionReuseCount(maxPoolSize);
				ods.setInitialPoolSize(maxPoolSize);
				ods.setSQLForValidateConnection("SELECT 1 from DUAL");
				ods.setValidateConnectionOnBorrow(true);
				ods.setInactiveConnectionTimeout(60);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return ods;
		}
	}

	static class GenericPoolDataSource implements DataSourceLoader {
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