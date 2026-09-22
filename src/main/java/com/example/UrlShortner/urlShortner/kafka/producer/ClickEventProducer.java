package com.example.UrlShortner.urlShortner.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.UrlShortner.urlShortner.kafka.event.ClickCount;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ClickEventProducer {
  
  private final KafkaTemplate<String, ClickCount> kafkaTemplate;

  public void publish(ClickCount event){
    
    kafkaTemplate.send(
      "click-events",
      event.shortUrl(),
      event
    );
    
  }  
}
