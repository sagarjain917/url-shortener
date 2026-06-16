package com.example.UrlShortner.urlShortner.Service.cache;

public interface CacheService {
  String get(String shortUrl);
  void set(String key, String originalUrl, int ttl);
  void delete(String url);
}