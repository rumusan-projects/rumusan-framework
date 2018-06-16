/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.cfg.AvailableSettings;
import org.rumusanframework.orm.jpa.validation.BeanValidationExceptionTranslator;
import org.rumusanframework.orm.jpa.vendor.ChainedHibernateJpaDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 *
 */
public abstract class DataSourceConfig {
	@Value("${" + AvailableSettings.DIALECT + "}")
	private String dialect;
	@Value("${" + AvailableSettings.SHOW_SQL + "}")
	private String showSql;
	@Value("${" + AvailableSettings.STATEMENT_BATCH_SIZE + "}")
	private String batchSize;
	@Value("${" + AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS + "}")
	private String sessionContextClass;

	protected abstract String getLogConfigLocation();

	protected abstract String getDatasourceDriverClassName();

	protected abstract String getDatasourceUrl();

	protected abstract String getDatasourceUsername();

	protected abstract String getDatasourcePassword();

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	private DataSourceContext getDataSourceContext() {
		DataSourceContext context = new DataSourceContext();

		context.setDriverClassName(getDatasourceDriverClassName());
		context.setUrl(getDatasourceUrl());
		context.setUsername(getDatasourceUsername());
		context.setPassword(getDatasourcePassword());

		return context;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings.DIALECT, dialect);
		properties.put(AvailableSettings.SHOW_SQL, showSql);
		properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, batchSize);
		properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, sessionContextClass);
		return properties;
	}

	private JpaDialect jpaDialect() {
		ChainedHibernateJpaDialect jpaDialect = new ChainedHibernateJpaDialect();
		jpaDialect.addTranslator(new BeanValidationExceptionTranslator());

		return jpaDialect;
	}

	private HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();
		jpaVendor.setDatabase(Database.HSQL);
		jpaVendor.setDatabasePlatform(dialect);

		return jpaVendor;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		DataSourceFactory dataSourceFactory = new DataSourceFactory();

		em.setDataSource(dataSourceFactory.getDataSource(getDataSourceContext()));
		em.setJpaDialect(jpaDialect());
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setPackagesToScan(getPackageToScan());
		em.setJpaProperties(getHibernateProperties());

		return em;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setJpaDialect(jpaDialect());

		return txManager;
	}

	protected abstract String[] getPackageToScan();
}