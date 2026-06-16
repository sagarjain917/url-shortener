package com.example.UrlShortner.urlShortner.exeption;

public class UrlNotFoundException extends RuntimeException {
  public UrlNotFoundException(String message){
    super(message);
  }
}
