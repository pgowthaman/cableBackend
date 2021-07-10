package com.cable.config;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cable.model.AreaModel;
import com.cable.model.ComplaintModel;
import com.cable.model.ComplaintTypeModel;
import com.cable.model.ConnectionModel;
import com.cable.model.OperatorModel;
import com.cable.model.PaymentModel;
import com.cable.model.ProviderModel;
import com.cable.model.SetupboxModel;
import com.cable.model.UserModel;
import com.cable.model.UserRoleModel;


/**
 * 
 * @author iamgo
 *
 */
@Configuration
@EnableTransactionManagement 
public class DBConfig {

	@Bean 
	public SessionFactory sessionFactory() throws URISyntaxException { 
		LocalSessionFactoryBuilder lsf= new LocalSessionFactoryBuilder(getDataSource()); 
		Properties hibernateProperties = new Properties();

		//hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL81Dialect");
		hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.setProperty("hibernate.show_sql", "false");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.use_sql_comments", "true");
		hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");

		lsf.addProperties(hibernateProperties); 
		lsf.addAnnotatedClass(AreaModel.class)
		.addAnnotatedClass(ComplaintModel.class)
		.addAnnotatedClass(ComplaintTypeModel.class)
		.addAnnotatedClass(ConnectionModel.class)
		.addAnnotatedClass(OperatorModel.class)
		.addAnnotatedClass(ProviderModel.class)
		.addAnnotatedClass(SetupboxModel.class)
		.addAnnotatedClass(UserModel.class)
		.addAnnotatedClass(PaymentModel.class)
		.addAnnotatedClass(UserRoleModel.class)
		; 
		return lsf.buildSessionFactory(); }

	/*@Bean 
	public DataSource getDataSource() throws URISyntaxException { 

		URI dbUri = new URI("postgres://zmclzpqmfckfgh:bd320530841f56f0a058727098269511e5c4bbe1ccc11819e38076a137a4a0bb@ec2-52-86-25-51.compute-1.amazonaws.com:5432/d9atvrga2nbf1o");

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

		BasicDataSource dataSource = new BasicDataSource(); dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password); 
		return dataSource; 
	}*/

	@Bean 
	public DataSource getDataSource() { 
		BasicDataSource dataSource = new BasicDataSource(); 
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/cable_db");
		dataSource.setUsername("root"); 
		dataSource.setPassword(""); 
		return dataSource; 
	}

	@Bean 
	public HibernateTransactionManager hibTransManager() throws URISyntaxException {
		HibernateTransactionManager htm = new HibernateTransactionManager();
	    htm.setTransactionSynchronization(HibernateTransactionManager.SYNCHRONIZATION_ALWAYS);
	    htm.setSessionFactory(sessionFactory());
		
		return htm; 
	} 
}
