/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

	private DataSourceFactory() {
		//
	}

	private static DataSource dataSource(DataSourceContext context) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(context.getDriverClassName());
		dataSource.setUrl(context.getUrl());
		dataSource.setUsername(context.getUsername());
		dataSource.setPassword(context.getPassword());

		return dataSource;
	}

	public static DataSource getDataSource(DataSourceContext context) {
		if (context.getDriverClassName().toLowerCase().contains("oracle")) {
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
		} else {
			// TODO: harvan. Using datasource each vendor.
			return dataSource(context);
		}
	}
}