package com.example.UrlShortner.urlShortner.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.UrlShortner.urlShortner.Service.UrlAnalyticService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UrlAnalyticController {
  
  private final UrlAnalyticService urlAnalyticService;

  @GetMapping("/Analytic/{shortUrl}")
  public ResponseEntity<?> getDailyClick(@PathVariable String shortUrl){
    
    urlAnalyticService.urlClickCount(shortUrl);
    
    return ResponseEntity.status(200).body("ok");
  }
}
