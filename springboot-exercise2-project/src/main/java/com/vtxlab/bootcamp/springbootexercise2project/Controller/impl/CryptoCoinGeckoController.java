package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

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
  private CryptoGeckoService cryptoGeckoService;

  @Override
  public ApiResponse<List<Market>> getMarkets(String currency, String... ids)
      throws JsonProcessingException {

    if (!(Currency.isValidCurrency(currency))) {
      throw new CoingeckoNotAvailableException(
          Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    }

    Currency cur = Currency.toCurrency(currency);

    List<Market> markets = new LinkedList<>();
    String key = "";
    String value = "";
    Market market;

    if (ids == null) {

      markets = cryptoGeckoService.getMarkets(cur);

      if (markets.size() == 0) {
        throw new CoingeckoNotAvailableException(
            Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
      }

      return ApiResponse.<List<Market>>builder() //
          .ok() //
          .data(markets) //
          .build();

    } else {

      markets = cryptoGeckoService.getMarkets(cur, ids);

      if (markets.size() == 0) {

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

}
