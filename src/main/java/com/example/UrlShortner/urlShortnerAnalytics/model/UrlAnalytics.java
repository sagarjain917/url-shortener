package com.example.UrlShortner.urlShortnerAnalytics.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="url_analytic")
public class UrlAnalytics {
  
  @Id
  private String id;

  private int urlId;
  private String os;
  private String browser;
  private String country;
  private String device;

  private LocalDateTime createdAt = LocalDateTime.now();
}