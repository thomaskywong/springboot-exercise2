package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.springbootexercise2project.Controller.CryptoCoinGeckoOperation;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.Service.impl.RedisService;
import com.vtxlab.bootcamp.springbootexercise2project.config.ScheduledConfig;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.exception.CoingeckoNotAvailableException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;
import com.vtxlab.bootcamp.springbootexercise2project.infra.CoinId;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;


@RestController
@RequestMapping(value = "/crypto/coingecko/api/v1")
public class CryptoCoinGeckoController implements CryptoCoinGeckoOperation {

  @Autowired
  private RedisService redisService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ScheduledConfig scheduledConfig;

  @Autowired
  private CryptoGeckoService cryptoGeckoService;

  @Override
  public ApiResponse<List<Market>> getMarkets(String currency, List<String> ids)
      throws JsonProcessingException {

    if (!(Currency.isValidCurrency(currency))) {
      // System.out.println("Invalid Currency.");
      throw new CoingeckoNotAvailableException(
          Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    }

    Duration duration = Duration
        .between(scheduledConfig.getCoingeckoUpdateTime(), LocalDateTime.now());
    if (duration.getSeconds() > 60) {
      // System.out.println("Timeout.");
      throw new CoingeckoNotAvailableException(
          Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    }
    System.out.println("delay time=" + duration);

    List<Market> markets = new LinkedList<>();
    String coinId = "";
    String key = "";
    String value = "";
    Market market;

    if (ids == null) {

      for (CoinId coin : CoinId.values()) {
        coinId = coin.name().toLowerCase();
        key = new StringBuilder("crytpo:coingecko:coin-markets:")
            .append(currency).append(":").append(coinId).toString();
        value = redisService.getValue(key);
        market = objectMapper.readValue(value, Market.class);
        markets.add(market);
      }

      return ApiResponse.<List<Market>>builder() //
          .OK() //
          .data(markets) //
          .build();

    } else {

      for (String id : ids) {

        if (!(CoinId.isValidCoinId(id))) {
          throw new CoingeckoNotAvailableException(
              Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
        }

        key = new StringBuilder("crytpo:coingecko:coin-markets:")
            .append(currency).append(":").append(id).toString();
        value = redisService.getValue(key);
        market = objectMapper.readValue(value, Market.class);
        markets.add(market);

      }

      return ApiResponse.<List<Market>>builder() //
          .OK() //
          .data(markets) //
          .build();
    }

  }

  @Override
  public ApiResponse<List<Coin>> getCoins() throws JsonProcessingException {
    List<Coin> coins = cryptoGeckoService.getCoins();
    return ApiResponse.<List<Coin>>builder() //
                      .OK() //
                      .data(coins) //
                      .build();
  }

}
