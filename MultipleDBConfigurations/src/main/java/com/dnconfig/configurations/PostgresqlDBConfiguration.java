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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.dnconfig.postgresql.repository",
		entityManagerFactoryRef = "postgresqlEntityManager",
		transactionManagerRef = "postgresqlTransactionManager"
		)
public class PostgresqlDBConfiguration {
	
//	@Bean
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
//        JpaVendorAdapter jpaVendorAdapter,
//        DataSource dataSource,
//        JpaProperties jpaProperties) {
//
//        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
//    }

	@Bean(name = "postgresqlDataSource")
	@ConfigurationProperties(prefix = "spring.postgresql.datasource")
	public DataSource postgresqlDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "postgresqlEntityManager")
	public LocalContainerEntityManagerFactoryBean postgresqlEntityManager(
			EntityManagerFactoryBuilder builder,
			@Qualifier("postgresqlDataSource") DataSource dataSource){
		return builder.dataSource(dataSource)
				.packages("com.dnconfig.postgresql.model")
				.persistenceUnit("postgresqlPU")
				.properties(postgresqlHibernateProperties())
				.build();
	}
	
	
	private Map<String, Object> postgresqlHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }
	
	@Bean(name = "postgresqlTransactionManager")
	public PlatformTransactionManager postgresqlTransactionManager(
			@Qualifier("postgresqlEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
