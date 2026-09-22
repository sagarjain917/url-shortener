package com.example.UrlShortner.urlShortner.kafka.event;

public record ClickCount(
  String shortUrl,
  String ip,
  String userAgent,
  long timeStamp
) { }
