package com.example.UrlShortner.urlShortner.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.UrlShortner.common.Cache.CacheKeys;
import com.example.UrlShortner.urlShortner.Dto.ShortenRequest;
import com.example.UrlShortner.urlShortner.Model.Url;
import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;
import com.example.UrlShortner.urlShortner.Repository.analytics.UrlAnalyticRepository;
import com.example.UrlShortner.urlShortner.Repository.url.UrlRepository;
import com.example.UrlShortner.urlShortner.Service.cache.CacheService;
import com.example.UrlShortner.urlShortner.exeption.RequestFailedException;
import com.example.UrlShortner.urlShortner.exeption.UrlNotFoundException;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlService {
  private final UrlAnalyticRepository urlAnalyticRepository;
  private final UrlRepository urlRepository;
  private final GenerateShortCode generateShortCode;
  private final CacheService cacheService;

  private static final Logger log = LoggerFactory.getLogger(UrlService.class);
  private static final int MAX_RETRIES = 3;
  
  public String shortUrlService(ShortenRequest request, long user) {

    log.info(
      "URL shorten request called shortUrlService={}",
      request.originalUrl()
    );

    Url existing = urlRepository
        .findByUserIdAndOriginalUrl(user, request.originalUrl())
        .orElse(null);

    if (existing != null) {
      return existing.getShortUrl();
    }

    for (int i = 0; i < MAX_RETRIES; i++) {
      try {
          return saveShortUrl(request, user);
      } catch (DuplicateKeyException e){
        log.warn(
          "Short URL collision occurred for originalUrl={}, retry={}", 
          request.originalUrl(),
          i + 1
        );
      }
    }

    log.error("URL request failed for originalUrl={}", request.originalUrl());

    throw new RequestFailedException("Request not Succeded");
  }


  @Transactional
  public String saveShortUrl(ShortenRequest request, long user) {

    String shortenUrl = generateShortCode.number();

    Url url = Url.builder()
        .originalUrl(request.originalUrl())
        .shortUrl(shortenUrl)
        .userId(user)
        .build();

    urlRepository.save(url);

    try{
      cacheService.set(shortenUrl, request.originalUrl(), 300);
    } catch(Exception e){
      log.error("cache failure for Url={}", request.originalUrl());
    }

    return shortenUrl;
  }

  public String getOriginalUrl(String shortUrl) {
    
    String key = CacheKeys.url(shortUrl);

    String cacheUrl = null;

    try{
      cacheUrl = cacheService.get(key);
    } catch(Exception e){
      log.error("cache failure for Url={}", key);
    }

    if(cacheUrl != null){
      return cacheUrl;
    }

    Url url = urlRepository.findByShortUrl(shortUrl)
            .orElseThrow(() -> new UrlNotFoundException("url not found"));

    try {
      cacheService.set(key, url.getOriginalUrl(), 300);
    } catch (ResponseStatusException e){
      log.info("server crash={}", e.getMessage());
    }

    urlObject(url);
    return url.getOriginalUrl();
  }

  private Url urlObject(Url url){
    return url;
  }
  
  @Transactional
  public void deleteUrlService(String shortUrl){
    
    Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));

    urlRepository.delete(url);
  
    cacheService.delete(CacheKeys.url(shortUrl));
  }
  
  @Async
  public void urlClickCount(String shortUrl, HttpServletRequest request){

    Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("url not found"));
    
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getRemoteAddr();
    }

    String userAgentString = request.getHeader("User-Agent");
    if (userAgentString == null) {
      userAgentString = "Unknown";
    }

    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

    Browser browser = userAgent.getBrowser();
    OperatingSystem os = userAgent.getOperatingSystem();

    String browserName = browser.getName();
    String osName = os.getName();
    String deviceType = os.getDeviceType().getName();
    
    UrlAnalytic urlAnalytic =
           UrlAnalytic.builder()
            .url(url)
            .os(osName)
            .browser(browserName)
            .country("india")
            .device(deviceType)
            .build();

    urlAnalyticRepository.save(urlAnalytic);
  }
}