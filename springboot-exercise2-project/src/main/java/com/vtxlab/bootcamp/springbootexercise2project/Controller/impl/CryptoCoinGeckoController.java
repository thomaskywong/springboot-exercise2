package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
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
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.MarketDTO;
import com.vtxlab.bootcamp.springbootexercise2project.exception.CoingeckoNotAvailableException;
import com.vtxlab.bootcamp.springbootexercise2project.exception.InvalidCoinException;
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
  public ApiResponse<List<Market>> getMarkets(String currency, String... ids)
      throws JsonProcessingException {

    // required rewrite logic. check 3rd if not available.
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
    // System.out.println("delay time=" + duration);

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
          .ok() //
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
          .ok() //
          .data(markets) //
          .build();
    }

  }

  @Override
  public ApiResponse<List<Coin>> getCoins() throws JsonProcessingException {
    List<Coin> coins = cryptoGeckoService.getCoins();

    return ApiResponse.<List<Coin>>builder() //
        .ok() //
        .data(coins) //
        .build();

  }

  @Override
  public List<Coin> getCoins2() throws JsonProcessingException {
    List<Coin> coins = cryptoGeckoService.getCoins();

    return coins;

  }

  @Override
  public List<Market> getMarkets2(String currency, String... ids)
      throws JsonProcessingException {

    Currency cur = Currency.toCurrency(currency);

    if (ids == null || (ids != null && ids.length == 0)) {

      return cryptoGeckoService.getMarkets(cur);

    } else {

      List<Coin> coins = cryptoGeckoService.getCoins();

      for (String id : ids) {
        if (!(Coin.isValidCoin(coins, id))) {
          throw new InvalidCoinException(Syscode.INVALID_COIN);
        }
      }

      return cryptoGeckoService.getMarkets(cur, ids);

    }



  }

  @Override
  public List<MarketDTO> getMarkets3() throws JsonProcessingException {

    return cryptoGeckoService.getMarkets(Currency.USD) //
        .stream() //
        .map(e -> MarketDTO.mapMarketDTO(e)) //
        .collect(Collectors.toList());

  }

  @Override
  public ApiResponse<List<MarketDTO>> getMarkets4()
      throws JsonProcessingException {

    List<MarketDTO> dto = cryptoGeckoService.getMarkets(Currency.USD) //
        .stream() //
        .map(e -> MarketDTO.mapMarketDTO(e)) //
        .collect(Collectors.toList());
    return ApiResponse.<List<MarketDTO>>builder() //
        .ok() //
        .data(dto) //
        .build();

  }

}
