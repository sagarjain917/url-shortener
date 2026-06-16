package com.example.UrlShortner.urlShortner.exeption;

public class RequestFailedException extends RuntimeException {
  public RequestFailedException(String message){
    super(message);
  }
}
