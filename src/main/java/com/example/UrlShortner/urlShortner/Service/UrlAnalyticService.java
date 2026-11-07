package com.example.UrlShortner.urlShortner.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.UrlShortner.urlShortner.Dto.response.AnalyticResponse;
import com.example.UrlShortner.urlShortner.Model.Url;
import com.example.UrlShortner.urlShortner.Repository.analytics.UrlAnalyticRepository;
import com.example.UrlShortner.urlShortner.Repository.url.UrlRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlAnalyticService {
  
  private final UrlAnalyticRepository urlAnalyticRepo;
  private final UrlRepository urlRepository;
  
  public AnalyticResponse urlClickCount (String shortUrl){
    
    Url url = urlRepository.findByShortUrl(shortUrl)
          .orElseThrow(() -> new RuntimeException("Url not found"));
   
    long urlId = url.getId();

    List<Object[]> countryList = urlAnalyticRepo.countByCountry(urlId);
    List<Object[]> browerList = urlAnalyticRepo.countByBrowser(urlId);
    List<Object[]> osList = urlAnalyticRepo.countByOs(urlId);
    List<Object[]> deviceList = urlAnalyticRepo.countByDevice(urlId);

    return new AnalyticResponse(
      countryList,
      browerList,
      osList,
      deviceList
    );
  }
}