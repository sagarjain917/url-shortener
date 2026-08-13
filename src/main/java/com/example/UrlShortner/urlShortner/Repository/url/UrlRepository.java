package com.example.UrlShortner.urlShortner.Repository.url;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UrlShortner.urlShortner.Model.Url;


public interface UrlRepository extends JpaRepository<Url, Long> { 
  Optional<Url> findByUserIdAndOriginalUrl(long user , String originalUrl);

  boolean existsByShortUrl(String shortUrl);

  Optional<Url> findByShortUrl(String shortUrl);

  Page<Url> findByUserId(long user, Pageable pageable);
}