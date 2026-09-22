package com.example.UrlShortner.urlShortner.kafka.consumer;

import com.example.UrlShortner.urlShortner.kafka.event.ClickCount;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;

import com.example.UrlShortner.urlShortner.exeption.UrlNotFoundException;
import com.example.UrlShortner.urlShortner.Model.Url;
import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;
import com.example.UrlShortner.urlShortner.Repository.analytics.UrlAnalyticRepository;
import com.example.UrlShortner.urlShortner.Repository.url.UrlRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service 
@AllArgsConstructor 
public class ClickEventConsumer {

  private final UrlAnalyticRepository urlAnalyticRepository;
  private final UrlRepository urlRepository;

  @KafkaListener(
    topics = "click-events",
    groupId = "analytics-group"
  )
  public void consume(ClickCount event){

    Url url = urlRepository.findByShortUrl(event.shortUrl()).
              orElseThrow(() -> new UrlNotFoundException("url not found"));

    UserAgent userAgent = UserAgent.parseUserAgentString(event.userAgent());
    
    Browser browser = userAgent.getBrowser();
    OperatingSystem os = userAgent.getOperatingSystem();

    String browserName = browser.getName();
    String osName = os.getName();
    String deviceType = os.getDeviceType().getName();
    
    UrlAnalytic urlAnalytic =
          UrlAnalytic.builder()
          .urlId(url.getUserId())
          .os(osName)
          .browser(browserName)
          .country("india")
          .device(deviceType)
          .build();

    urlAnalyticRepository.save(urlAnalytic);
  }
}
