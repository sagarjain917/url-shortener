package com.example.UrlShortner.urlShortner.Service.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService implements CacheService {

  private final RedisTemplate<String, String> redisTemplate;

  public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public String get(String shortUrl) {
    return redisTemplate.opsForValue().get(shortUrl);
  }
 
  @Override
  public void set(String key, String shortUrl, int ttl) {
    redisTemplate.opsForValue().set(key, shortUrl, 10);
  }

  @Override
  public void delete(String shortUrl) {
    redisTemplate.delete(shortUrl);
  }

}