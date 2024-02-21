package com.vtxlab.bootcamp.springbootexercise2project.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.CoinId;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.RedisHelper;
import lombok.Getter;

@Getter
@Component
@EnableScheduling
public class ScheduledConfig {

  private LocalDateTime coingeckoUpdateTime = LocalDateTime.MIN;

  @Autowired
  private CryptoGeckoService cGeckoService;

  @Autowired
  private RedisHelper redisHelper;

  @Scheduled(fixedRate = 30000)
  public void fixedRateTask() {

    try {

      // System.out.println(this.coingeckoUpdateTime);
      // List<Coin> coins = cGeckoService.getCoins();

      for (Currency cur : Currency.values()) {

        String currency = cur.name().toLowerCase();

        for (CoinId coin : CoinId.values()) {
          // System.out.println("currency=" + cur.name() + ", id=" + coin.getId());

          String coinId = coin.getId();
          String[] ids = new String[1];
          ids[0] = coinId;

          List<Market> markets = cGeckoService.getMarkets(cur, ids);

          String key = new StringBuilder("crytpo:coingecko:coin-markets:").append(currency).append(":").append(coinId).toString();
          // System.out.println(key);
          Market market = markets.get(0);
          redisHelper.set(key, market);
        }
      }

      coingeckoUpdateTime = LocalDateTime.now();
      // System.out.println(this.coingeckoUpdateTime);


    } catch (RestClientException | JsonProcessingException ex) {

    }
  }
}
