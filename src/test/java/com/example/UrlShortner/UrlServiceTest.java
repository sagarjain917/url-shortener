package com.example.UrlShortner;

import com.example.UrlShortner.urlShortner.Repository.url.UrlRepository;
import com.example.UrlShortner.urlShortner.Service.UrlService;
import com.example.UrlShortner.urlShortner.Model.Url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;

@ExtendWith(MockitoException.class)
class UrlServiceTest {
  
  @Mock
  private UrlRepository urlRepository;

  @InjectMocks
  private UrlService urlService;

  @Test 
  void ShouldReturnOriginalUrl(){
    Url url = new Url();
    url.setShortUrl("abc");
    url.setOriginalUrl("http://google.com");

    when(urlRepository.findByShortUrl("abc"))
        .thenReturn(Optional.ofNullable(null));

    String result = urlService.getOriginalUrl("abc");

    assertEquals("http://google.com", result);
  }
}
