package com.example.UrlShortner;

import com.example.UrlShortner.urlShortner.Repository.url.UrlRepository;
import com.example.UrlShortner.urlShortner.Service.GenerateShortCode;
import com.example.UrlShortner.urlShortner.Service.UrlService;
import com.example.UrlShortner.urlShortner.cache.CacheService;
import com.example.UrlShortner.urlShortner.exeption.UrlNotFoundException;
import com.example.UrlShortner.urlShortner.Dto.ShortenRequest;
import com.example.UrlShortner.urlShortner.Model.Url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
  
  @Mock
  private UrlRepository urlRepository;

  @Mock
  private CacheService cacheService;

  @Mock 
  private GenerateShortCode generateShortCode;

  @InjectMocks
  private UrlService urlService;

  @Test
  void createShortUrl(){
    Url url = new Url();
    url.setShortUrl("abc");
    
    when(urlRepository.save(url))
      .thenReturn(url);

    Url saveUrl = urlRepository.save(url);

    assertEquals("abc", saveUrl.getShortUrl());
    verify(urlRepository).save(url);
  }

  @Test 
  void ShouldReturnOriginalUrl(){
    Url url = new Url();
    url.setShortUrl("abc");
    url.setOriginalUrl("http://google.com");

    when(urlRepository.findByShortUrl("abc"))
        .thenReturn(Optional.of(url));

    String result = urlService.getOriginalUrl("abc");

    assertEquals("http://google.com", result);
    verify(urlRepository).findByShortUrl("abc");
  }

  @Test 
  void shouldReturnExistingShortUrl(){
    ShortenRequest shortenRequest = new ShortenRequest("http://google.com");
  
    Url existingUrl = Url.builder()
        .originalUrl("https://google.com")
        .shortUrl("abc")
        .userId(1L)
        .build();
  
    when(urlRepository.findByUserIdAndOriginalUrl(1L, "http://google.com"))
      .thenReturn(Optional.of(existingUrl));
  
  
    String result = urlService.shortUrlService(shortenRequest, 1L);

    assertEquals("abc", result);
    verify(urlRepository, never()).save(any(Url.class));
  }

  @Test
  void shouldReturnOriginalUrlFromCache() {
    when(cacheService.get(anyString()))
        .thenReturn("https://google.com");

    String result = urlService.getOriginalUrl("abc");

    assertEquals("https://google.com", result);

    verify(urlRepository, never()).findByShortUrl("abc");
  }

  void shouldFetchOriginalUrl(){
  
    Url existingUrl = Url.builder()
        .originalUrl("https://google.com")
        .shortUrl("abc")
        .userId(1L)
        .build();
  
    when(cacheService.get(anyString()))
      .thenReturn(null);

    when(urlRepository.findByShortUrl("abc"))
      .thenReturn(Optional.of(existingUrl));
    
    String result = urlService.getOriginalUrl("abc");

    assertEquals("https://google.com", result);
    verify(urlRepository).findByShortUrl("abc");
    verify(cacheService).set(
      anyString(), eq("https://google.com"), eq(300)
    );
  }

  @Test 
  void shouldThrowExceptionWhenUrlNotExist(){

    when(cacheService.get(anyString()))
    .thenReturn(null);

    when(urlRepository.findByShortUrl("abc"))
    .thenReturn(Optional.empty());

    assertThrows(
      UrlNotFoundException.class, 
      () -> urlService.getOriginalUrl("abc")
    );
  }

  @Test 
  void shouldRetryWheTheSameShortCodeExist(){
    ShortenRequest request =
        new ShortenRequest("https://google.com");

    Url existing = Url.builder()
        .shortUrl("abc")
        .originalUrl("https://google.com")
        .userId(1L)
        .build();

    when(urlRepository.findByUserIdAndOriginalUrl(
        1L, "https://google.com"
    )).thenReturn(Optional.empty());
 
    when(generateShortCode.number())
        .thenReturn("abc", "abc2");

    when(urlRepository.save(any(Url.class)))
        .thenThrow(new DuplicateKeyException("Duplicate key"))
        .thenAnswer(invocation -> invocation.getArgument(0));
  
    String result = urlService.shortUrlService(request, 1L);

    assertEquals("abc2", result);

    verify(urlRepository, times(2)).save(any(Url.class));
  }
}