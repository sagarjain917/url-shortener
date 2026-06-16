package com.example.UrlShortner.urlShortner.Service.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCacheService implements CacheService  {

  private final Map<String, String> cache = new ConcurrentHashMap<>();

  @Override
  public String get(String key) {
    return cache.get(key);
  }

  @Override
  public void set(String key, String originalUrl, int ttl) {
    cache.put(key, key);
  }

  @Override
  public void delete(String key) {
    cache.remove(key);
  }
}