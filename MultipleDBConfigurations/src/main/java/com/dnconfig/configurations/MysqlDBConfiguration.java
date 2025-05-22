package com.dnconfig.configurations;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.dnconfig.mysql.repository",
		entityManagerFactoryRef = "mysqlEntityManager",
		transactionManagerRef = "mysqlTransactionManager"
		)
public class MysqlDBConfiguration {
	
//	@Primary
//	@Bean
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
//        JpaVendorAdapter jpaVendorAdapter,
//        DataSource dataSource,
//        JpaProperties jpaProperties) {
//
//        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
//    }
	
	@Primary
	@Bean(name="mysqlDataSource")
	@ConfigurationProperties(prefix = "spring.mysql.datasource")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "mysqlEntityManager")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManager(
			EntityManagerFactoryBuilder builder, 
			@Qualifier("mysqlDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("com.dnconfig.mysql.model")
				.persistenceUnit("mysqlPU")
				.properties(mysqlHibernateProperties())
				.build();
	}
	
	private Map<String, Object> mysqlHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }
	
	@Primary
	@Bean(name = "mysqlTransactionManager")
	public PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	

}
