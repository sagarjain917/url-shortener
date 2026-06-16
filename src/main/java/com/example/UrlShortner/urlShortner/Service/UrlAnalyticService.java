package com.example.UrlShortner.urlShortner.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.UrlShortner.urlShortner.Dto.response.AnalyticResponse;
import com.example.UrlShortner.urlShortner.Repository.UrlAnalyticRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlAnalyticService {
  
  private final UrlAnalyticRepository urlAnalyticRepo;

  public AnalyticResponse urlClickCount (String shortUrl){
    List<Object[]> countryList = urlAnalyticRepo.countByCountry(shortUrl);
    List<Object[]> browerList = urlAnalyticRepo.countByBrowser(shortUrl);
    List<Object[]> osList = urlAnalyticRepo.countByOs(shortUrl);
    List<Object[]> deviceList = urlAnalyticRepo.countByDevice(shortUrl);

    return new AnalyticResponse(
      countryList,
      browerList,
      osList,
      deviceList
    );
  }
}