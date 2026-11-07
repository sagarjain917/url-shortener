package com.example.UrlShortner.urlShortner.Repository.analytics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.UrlShortner.urlShortner.Model.UrlAnalytic;

public interface UrlAnalyticRepository extends JpaRepository<UrlAnalytic, Long> {

  @Query("""
    SELECT u.os, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.urlId = :urlId
    GROUP BY u.os
  """)
  List<Object[]> countByOs(@Param("urlId") Long urlId);
  
  @Query("""
    SELECT u.browser, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.urlId = :urlId
    GROUP BY u.browser
  """)
  List<Object[]> countByBrowser(@Param("urlId") Long urlId);
  
  @Query("""
    SELECT u.country, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.urlId = :urlId
    GROUP BY u.country
  """)
  List<Object[]> countByCountry(@Param("urlId") Long urlId);
  
  @Query("""
    SELECT u.device, COUNT(u)
    FROM UrlAnalytic as u
    WHERE u.urlId = :urlId
    GROUP BY u.device
  """)
  List<Object[]> countByDevice(@Param("urlId") Long urlId);
}