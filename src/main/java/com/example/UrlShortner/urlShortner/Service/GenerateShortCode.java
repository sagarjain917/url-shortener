package com.example.UrlShortner.urlShortner.Service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class GenerateShortCode {
  private static final String KEY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";
  private static final int LENGTH = 7;
  private static final SecureRandom secureRandom = new SecureRandom();

  public String number() {
    StringBuilder value = new StringBuilder(LENGTH);
    for(int i = 0; i < LENGTH; i++){
      int index = secureRandom.nextInt(KEY.length());
      value.append(KEY.charAt(index));
    }
    return value.toString();
  }
}
