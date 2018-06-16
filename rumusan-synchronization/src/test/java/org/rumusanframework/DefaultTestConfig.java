/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework;

import org.rumusanframework.concurrent.config.Settings;
import org.rumusanframework.orm.config.DataSourceConfig;
import org.rumusanframework.orm.config.Log4j2Configurer;
import org.rumusanframework.orm.dao.BasePackageRumusanOrmDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Configuration
@ComponentScan(basePackages = { BasePackageRumusanOrmDao.PACKAGE })
@EnableTransactionManagement
@PropertySource(value = { "${" + Settings.CONFIG_LOCATION + ":classpath:application-test.properties}" })
public abstract class DefaultTestConfig extends DataSourceConfig {
	@Value("${" + Settings.LOG_CONFIG_LOCATION + "}")
	private String location;
	@Value("${" + Settings.DATASOURCE_DRIVER_CLASS + "}")
	private String datasourceDriverClassName;
	@Value("${" + Settings.DATASOURCE_URL + "}")
	private String datasourceUrl;
	@Value("${" + Settings.DATASOURCE_USERNAME + "}")
	private String datasourceUsername;
	@Value("${" + Settings.DATASOURCE_SECRET + "}")
	private String datasourcePassword;

	@Override
	protected String getLogConfigLocation() {
		return location;
	}

	@Override
	protected String getDatasourceDriverClassName() {
		return datasourceDriverClassName;
	}

	@Override
	protected String getDatasourceUrl() {
		return datasourceUrl;
	}

	@Override
	protected String getDatasourceUsername() {
		return datasourceUsername;
	}

	@Override
	protected String getDatasourcePassword() {
		return datasourcePassword;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Log4j2Configurer log4jInitialization() {
		Log4j2Configurer log4jConfig = new Log4j2Configurer();
		log4jConfig.setLocation(getLogConfigLocation());

		return log4jConfig;
	}
}