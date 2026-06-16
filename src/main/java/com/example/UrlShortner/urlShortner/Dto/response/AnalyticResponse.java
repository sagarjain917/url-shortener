package com.example.UrlShortner.urlShortner.Dto.response;

import java.util.List;

public record AnalyticResponse(
  List<Object[]> country,
  List<Object[]> browser,
  List<Object[]> os,
  List<Object[]> device
)
{}
