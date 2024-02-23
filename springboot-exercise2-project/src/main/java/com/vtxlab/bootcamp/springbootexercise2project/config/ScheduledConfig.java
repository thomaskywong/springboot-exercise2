package com.vtxlab.bootcamp.springbootexercise2project.config;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.Service.impl.RedisService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.CoinId;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@EnableScheduling
public class ScheduledConfig {

  private LocalDateTime coingeckoUpdateTime = LocalDateTime.MIN;

  @Autowired
  private CryptoGeckoService cryptoGeckoService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private RedisService redisService;

  @Scheduled(fixedRate = 30000)
  public void fixedRateTask() {

    try {

      cryptoGeckoService.getJPHdataToRedis();
      coingeckoUpdateTime = LocalDateTime.now();

    } catch (RestClientException | JsonProcessingException ex) {

    }
  }
}
