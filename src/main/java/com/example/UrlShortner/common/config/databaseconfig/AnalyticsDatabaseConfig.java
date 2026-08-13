package com.example.UrlShortner.common.config.databaseconfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages="com.example.UrlShortner.urlShortner.Repository.analytics",
  entityManagerFactoryRef="analyticManagerFactory",
  transactionManagerRef="analyticTransactionManager"
)
public class AnalyticsDatabaseConfig {
  
  @Bean
  @Qualifier("analytic")
  @ConfigurationProperties(prefix="analytics.datasource")
  public DataSource analyticDataSource(){
    return DataSourceBuilder
      .create()
      .build();
  }

  @Bean
  @Qualifier("analytic")
  public JpaProperties analyticJpaProperties() {
    return new JpaProperties();
  }

  @Qualifier("analytic")
  @Bean
  public LocalContainerEntityManagerFactoryBean analyticManagerFactory(
    @Qualifier("analytic") DataSource dataSource,
    @Qualifier("analytic") JpaProperties jpaProperties){
    
      EntityManagerFactoryBuilder builder = 
            analyticEntityManagerFactoryBuilder(jpaProperties);

      return builder
      .dataSource(dataSource)
      .packages(UrlAnalytic.class)
      .persistenceUnit("urlAnalytic")
      .build();
  }

  private EntityManagerFactoryBuilder analyticEntityManagerFactoryBuilder(JpaProperties jpaProperties){
    
    JpaVendorAdapter JpaVendorAdapter = analyticJpaVendorAdapter(jpaProperties);
    
    Function<DataSource, Map<String, ?>> jpaPropertiesFactory = 
            datasource -> analyticJpaProperties(
              datasource, 
              jpaProperties.getProperties()
          );
    
    return new EntityManagerFactoryBuilder(JpaVendorAdapter, jpaPropertiesFactory, null);
  }

  private JpaVendorAdapter analyticJpaVendorAdapter(JpaProperties jpaProperties){
    return new HibernateJpaVendorAdapter();
  }

  private Map<String, ?> analyticJpaProperties(
    DataSource dataSource,
    Map<String, ?>existingProperties){

      return new LinkedHashMap<>(existingProperties);
  }

  @Bean
  public PlatformTransactionManager analyticTransactionManager(
    @Qualifier("analyticManagerFactory")
    EntityManagerFactory entityManagerFactory) {
      return new JpaTransactionManager(entityManagerFactory);
  }
}