package com.example.UrlShortner.urlShortner.Dto;

import jakarta.validation.constraints.NotBlank;

public record ShortenRequest(

  @NotBlank
  String originalUrl
) { }
