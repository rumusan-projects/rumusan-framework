package org.rumusanframework;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.rumusanframework.orm.jpa.validation.BeanValidationExceptionTranslator;
import org.rumusanframework.orm.jpa.vendor.ChainedHibernateJpaDialect;
import org.rumusanframework.repository.dao.BasePackageRumusanRepositoryDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Configuration
@ComponentScan(basePackages = { BasePackageRumusanRepositoryDao.PACKAGE })
@EnableTransactionManagement
@PropertySource(value = { "file:/opt/synchronize/config/application-test.properties" })
public abstract class DefaultTestConfig {
	@Value("${" + AvailableSettings.DIALECT + "}")
	private String dialect;
	@Value("${" + AvailableSettings.SHOW_SQL + "}")
	private String showSql;
	@Value("${" + AvailableSettings.STATEMENT_BATCH_SIZE + "}")
	private String batchSize;
	@Value("${" + AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS + "}")
	private String sessionContextClass;
	@Value("${test.synchronize.datasource.driver.class.name}")
	private String datasourceDriverClassName;
	@Value("${test.synchronize.datasource.url}")
	private String datasourceUrl;
	@Value("${test.synchronize.datasource.username}")
	private String datasourceUsername;
	@Value("${test.synchronize.datasource.password}")
	private String datasourcePassword;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Log4j2Configurer log4jInitialization(@Value("${test.synchronize.log.config}") String location) {
		Log4j2Configurer log4jConfig = new Log4j2Configurer();
		log4jConfig.setLocation(location);

		return log4jConfig;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(datasourceDriverClassName);
		dataSource.setUrl(datasourceUrl);
		dataSource.setUsername(datasourceUsername);
		dataSource.setPassword(datasourcePassword);

		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings.DIALECT, dialect);
		properties.put(AvailableSettings.SHOW_SQL, showSql);
		properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, batchSize);
		properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, sessionContextClass);
		return properties;
	}

	public JpaDialect jpaDialect() {
		ChainedHibernateJpaDialect dialect = new ChainedHibernateJpaDialect();
		dialect.addTranslator(new BeanValidationExceptionTranslator());

		return dialect;
	}

	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();
		jpaVendor.setDatabase(Database.HSQL);
		jpaVendor.setDatabasePlatform(dialect);

		return jpaVendor;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource());
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