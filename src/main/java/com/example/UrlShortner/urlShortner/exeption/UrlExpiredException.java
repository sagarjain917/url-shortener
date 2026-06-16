package com.example.UrlShortner.urlShortner.exeption;

public class UrlExpiredException extends RuntimeException {
  public UrlExpiredException(String message){
    super(message);
  }
}
