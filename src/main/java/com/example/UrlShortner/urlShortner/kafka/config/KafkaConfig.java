package com.example.UrlShortner.urlShortner.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaConfig {
  
  @Bean
  public NewTopic analyticTopic(){
    return TopicBuilder
      .name("click-events")
      .partitions(4)
      .replicas(2)
      .build();
  }

  @Bean
  public NewTopic notificationTopic(){
    return TopicBuilder
      .name("notification-events")
      .partitions(4)
      .replicas(2)
      .build();
  }  
}
