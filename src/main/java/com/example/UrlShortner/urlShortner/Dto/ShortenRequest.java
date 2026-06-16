package com.example.UrlShortner.urlShortner.Dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record ShortenRequest(

  @NotBlank
  String originalUrl,
  
  LocalDateTime expiredAt
) { }
