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

import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages="com.example.UrlShortner.urlShortner.Repository.analytics",
  entityManagerFactoryRef="analyticManagerFactory",
  transactionManagerRef="analyticTransactinManager"
)
public class AnalyticsDatabaseConfig {
  
  @Bean
  @ConfigurationProperties(prefix="analytics.datasource")
  public DataSource analyticDataSource(){
    return DataSourceBuilder
      .create()
      .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean analyticManagerFactory(
    EntityManagerFactoryBuilder builder){
    return builder
      .dataSource(analyticDataSource())
      .packages(UrlAnalytic.class)
      .persistenceUnit("urlAnalytic")
      .build();
  }

  @Bean
  public PlatformTransactionManager analyticTransactinManager(
    @Qualifier("analyticManagerFactory")
    EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
  }
}
