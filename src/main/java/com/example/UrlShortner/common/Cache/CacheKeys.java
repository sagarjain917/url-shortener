package com.example.UrlShortner.common.Cache;

import org.springframework.stereotype.Component;

@Component
public class CacheKeys {

  public static String url(String shortUrl) {
    return "url:" + shortUrl; 
  } 

}