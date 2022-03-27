package com;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com"})
@PropertySource(value = {"classpath:application.properties"})

public class HibernateConfiguration {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
		return dataSource;
	}
	
	private Properties hibernateProperties() {
		Properties properties=new Properties();
		properties.put("hibernate.dailect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show_sql",environment.getRequiredProperty("spring.jpa.show-sql"));
		properties.put("hibernate.connection.username", environment.getRequiredProperty("spring.datasource.username"));
        properties.put("hibernate.connection.password", environment.getRequiredProperty("spring.datasource.password"));
        properties.setProperty("hibernate.hbm2ddl.auto",environment.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
        return properties;
	}
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager=new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}
