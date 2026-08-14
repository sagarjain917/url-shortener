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
  @Qualifier("url")
  @ConfigurationProperties(prefix="url.datasource")
  public DataSource urlDataSource(){
    return DataSourceBuilder
        .create()
        .build();
  }

  @Bean
  @Qualifier("url")
  public JpaProperties jpaProperties(){
    return new JpaProperties();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean urlEntityManagerFactory(
    @Qualifier("url") DataSource dataSource,
    @Qualifier("url") JpaProperties jpaProperties) {

    EntityManagerFactoryBuilder builder = 
        urlEntityManagerFactoryBuilder(jpaProperties);
    
    return builder
      .dataSource(dataSource)
      .packages(Url.class)
      .persistenceUnit("url")
      .build();
  }

  private EntityManagerFactoryBuilder urlEntityManagerFactoryBuilder(JpaProperties jpaProperties){
    
    JpaVendorAdapter jpaVendorAdapter = urlJpaVendorAdapter(jpaProperties);

    Function<DataSource, Map<String, ?>> jpaPropertiesFactory = 
          dataSource -> urlJpaProperties(
            dataSource,
            jpaProperties.getProperties()
          );

    return new EntityManagerFactoryBuilder(
      jpaVendorAdapter, 
      jpaPropertiesFactory, 
      null
    );
  }

  private Map<String, ?> urlJpaProperties(
    DataSource dataSource,
    Map<String, ?> existingProperties){

      Map<String, Object> properties = new LinkedHashMap<>(); 
      
      properties.putAll(existingProperties);

      properties.put("hibernate.hbm2ddl.auto", "update");
    
      return properties;
  }

  private JpaVendorAdapter urlJpaVendorAdapter(JpaProperties jpaProperties){
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  public PlatformTransactionManager urlTransactionManager(
    @Qualifier("urlEntityManagerFactory")
    EntityManagerFactory entityManagerFactory){
      return new JpaTransactionManager(entityManagerFactory);
  }
}
