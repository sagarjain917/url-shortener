package com.example.UrlShortner.urlShortner.pagination.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.UrlShortner.urlShortner.Model.Url;
import com.example.UrlShortner.urlShortner.Repository.UrlRepository;
import com.example.UrlShortner.urlShortner.pagination.dto.ResponseDto;
import com.example.UrlShortner.user.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaginationService {

  private final UrlRepository urlRepository; 

  public Page<ResponseDto> paginateUrl(int page, int size, User user) {
    
    Pageable pageable = PageRequest.of(page, size);

    Page<Url> urls = urlRepository.findByUser(user, pageable);
  
    return urls.map(url -> 
        new ResponseDto(
          url.getUser().getId(),
          url.getOriginalUrl(),
          url.getShortUrl(),
          url.getCreatedAt()
      )
    );
  }
}