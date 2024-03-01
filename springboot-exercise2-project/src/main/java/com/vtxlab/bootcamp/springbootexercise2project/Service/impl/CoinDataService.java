package com.vtxlab.bootcamp.springbootexercise2project.Service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.springbootexercise2project.Service.ApiService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.CoinData;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Scheme;
import com.vtxlab.bootcamp.springbootexercise2project.mapper.UriCompBuilder;

@Service
public class CoinDataService implements ApiService<CoinData> {

  @Value(value = "${api.jph.domain}")
  private String domain;

  @Value(value = "${api.jph.basepath}")
  private String basepath;

  @Value(value = "${api.jph.endpoints.markets}")
  private String endpointMarkets;

  @Value(value = "${api.jph.endpoints.list}")
  private String endpointCoins;

  @Value(value = "${api.jph.key}")
  private String key;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public List<CoinData> fetchData() {

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        endpointMarkets, Currency.USD, key);

    System.out.println(urlString);

    // String apiUrl =
    //     "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd";

    CoinData[] response = restTemplate.getForObject(urlString, CoinData[].class);
    // System.out.println(Arrays.toString(response));
    return Arrays.stream(response).collect(Collectors.toList());
  }

}
