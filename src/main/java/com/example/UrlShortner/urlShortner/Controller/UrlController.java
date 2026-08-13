package com.example.UrlShortner.urlShortner.Controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UrlShortner.urlShortner.Dto.ShortenRequest;
import com.example.UrlShortner.urlShortner.Service.UrlService;
import com.example.UrlShortner.urlShortner.pagination.dto.ResponseDto;
import com.example.UrlShortner.urlShortner.pagination.service.PaginationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UrlController{
  
  private final UrlService urlService;
  private final PaginationService paginationService;

  @PostMapping("/shorten")
  public ResponseEntity<?> shortUrl(@Valid @RequestBody ShortenRequest request, long user){
    
    String shortUrl = urlService.shortUrlService(request, user);
    
    return ResponseEntity.ok(shortUrl);
  }

  @GetMapping("/{shortUrl}")
  public void redirect(@PathVariable String shortUrl,
                        HttpServletResponse response,
                        HttpServletRequest request) throws IOException
  {
    String original = urlService.getOriginalUrl(shortUrl);
    urlService.urlClickCount(shortUrl, request);
    response.sendRedirect(original);
  }

  
  @DeleteMapping("/Url/{request}")
  public ResponseEntity<?> deleteUrl(@PathVariable String request){
    urlService.deleteUrlService(request);
    
    return ResponseEntity.ok("URL Deleted");
  }

  
  @GetMapping("/")
  public ResponseEntity<?> getAllUrl(
    @RequestParam(defaultValue = "0")
    int page,

    @RequestParam(defaultValue = "10")
    int size,

    @RequestParam(required = false) long user
    
  ){

    Page<ResponseDto> urls = paginationService.paginateUrl(page, size, user);
    
    return ResponseEntity.ok(urls);
  }
}