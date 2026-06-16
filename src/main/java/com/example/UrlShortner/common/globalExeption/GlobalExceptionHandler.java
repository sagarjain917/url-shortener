package com.example.UrlShortner.common.globalExeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.UrlShortner.urlShortner.exeption.UrlExpiredException;
import com.example.UrlShortner.urlShortner.exeption.UrlNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(UrlNotFoundException.class)
  public ResponseEntity<?> handleUrlNotFound(UrlNotFoundException ex){
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
  }

  @ExceptionHandler(UrlExpiredException.class)
  public ResponseEntity<?> handleUrlExpired(UrlExpiredException ex){
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
  }
}