package com.example.UrlShortner.common.config.redisconfig;

import java.time.Duration;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
  
  @Bean
  public RedisConnectionFactory redisConnectionFactory(){

    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration("localhost", 6379);

    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
      .build();

    return new LettuceConnectionFactory(redisConfig, clientConfig); 
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
    RedisTemplate<String, String> template = new RedisTemplate<>();
    
    template.setConnectionFactory(factory);
    
    StringRedisSerializer serializer = new StringRedisSerializer();
    template.setKeySerializer(serializer);
    template.setValueSerializer(serializer);
    template.setHashKeySerializer(serializer);
    template.setHashValueSerializer(serializer);
   
    return template;
  }
}