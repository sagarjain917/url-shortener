package com.example.UrlShortner.urlShortner.Repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UrlShortner.urlShortner.Model.Url;
import com.example.UrlShortner.user.User;

public interface UrlRepository extends JpaRepository<Url, Long> { 
  Optional<Url> findByUserAndOriginalUrl(User user , String originalUrl);

  boolean existsByShortUrl(String shortUrl);

  Optional<Url> findByShortUrl(String shortUrl);

  Page<Url> findByUser(User user, Pageable pageable);
}