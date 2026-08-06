package com.example.UrlShortner.urlShortner.pagination.dto;

import java.time.LocalDateTime;

public record ResponseDto(
  long userId,
  String shortUrl,
  String originalUrl,
  LocalDateTime createdAt
) {}