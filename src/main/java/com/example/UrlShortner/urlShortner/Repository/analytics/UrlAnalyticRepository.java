package com.example.UrlShortner.urlShortner.Repository.analytics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;

@Repository
public interface UrlAnalyticRepository extends JpaRepository<UrlAnalytic, Long> {

  @Query("""
    SELECT u.os, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.url.shortUrl = :shortUrl
    GROUP BY u.os
  """)
  List<Object[]> countByOs(String shortUrl);
  
  @Query("""
    SELECT u.browser, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.url.shortUrl = :shortUrl
    GROUP BY u.browser
  """)
  List<Object[]> countByBrowser(String shortUrl);
  
  @Query("""
    SELECT u.country, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.url.shortUrl = :shortUrl
    GROUP BY u.country
  """)
  List<Object[]> countByCountry(String shortUrl);
  
  @Query("""
    SELECT u.device, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.url.shortUrl = :shortUrl
    GROUP BY u.device
  """)
  List<Object[]> countByDevice(String shortUrl);
}