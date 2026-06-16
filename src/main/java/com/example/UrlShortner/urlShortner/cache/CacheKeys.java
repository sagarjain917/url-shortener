package com.example.UrlShortner.urlShortner.cache;

import org.springframework.stereotype.Component;

@Component
public class CacheKeys {

  public static String url(String shortUrl) {
    return "url:" + shortUrl; 
  } 

}