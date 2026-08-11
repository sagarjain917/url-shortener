package com.example.UrlShortner.common.config.databaseconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.UrlShortner.urlShortner.Model.Url;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages="com.example.UrlShortner.urlShortner.Repository.url",
  entityManagerFactoryRef="urlEntityManagerFactory",
  transactionManagerRef="urlTransactionManager"
)
public class UrlDatabaseConfig {
 
  @Bean
  @ConfigurationProperties(prefix="url.datasource")
  public DataSource urlDataSource(){
    return DataSourceBuilder
        .create()
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean urlEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(urlDataSource())
        .packages(Url.class)
        .persistenceUnit("url")
        .build();          
  }

  @Bean
  public PlatformTransactionManager urlTransactionManager(
    @Qualifier("urlEntityManagerFactory")
    EntityManagerFactory entityManagerFactory){
      return new JpaTransactionManager(entityManagerFactory);
  }
}
