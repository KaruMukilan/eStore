package com.niit.ecomm.hiber.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.ecomm.hiber.domain.MProduct;

@Configuration
@ComponentScan({"com.niit.ecomm.hiber"})
@EnableTransactionManagement
public class HibernationConfig {
	@Autowired
	@Bean(name ="dataSource")
	public DataSource dataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:tcp://localhost/~/floral");
		ds.setUsername("sa");
		ds.setPassword("");
		return ds;
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.hbm2ddl.auto", "update");
		return prop;
	}

	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		builder.addProperties(getHibernateProperties());
		// builder.scanPackages("com.niit.ecomm_backend");
		builder.addAnnotatedClass(MProduct.class);
/*		builder.addAnnotatedClass(Category.class);
		builder.addAnnotatedClass(Supplier.class);
		builder.addAnnotatedClass(User.class);
		builder.addAnnotatedClass(Order.class);		
		builder.addAnnotatedClass(CartI.class);*/
		return builder.buildSessionFactory();
	}

	// Create a transaction manager
/*	@Bean
	@Autowired
	public HibernateTransactionManager txManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}*/

}