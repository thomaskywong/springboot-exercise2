package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

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
import com.vtxlab.bootcamp.springbootexercise2project.exception.InvalidCurrencyException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;
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
      throws Exception {

    if (!(Currency.isValidCurrency(currency))) {
      throw new InvalidCurrencyException(Syscode.INVALID_CURRENCY);
    }

    Currency cur = Currency.toCurrency(currency);

    List<Market> markets = cryptoGeckoService.getMarkets(cur, ids);


    // List<Market> markets = new LinkedList<>();
    // String key = "";
    // String value = "";
    // Market market;

    // if (ids == null) {
    //   try {
    //     markets = cryptoGeckoService.getMarkets(cur);
    //   } catch (RestClientException ex) {
    //     throw new CoingeckoNotAvailableException(
    //         Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    //   }


      return ApiResponse.<List<Market>>builder() //
          .ok() //
          .data(markets) //
          .build();

    // } else {

    //   try {
    //     markets = cryptoGeckoService.getMarkets(cur, ids);
    //   } catch (Exception ex) {

    //   }

    //   if (markets.size() == 0) {

    //     for (String id : ids) {

    //       if (!(CoinId.isValidCoinId(id))) {
    //         throw new CoingeckoNotAvailableException(
    //             Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    //       }

    //       key = new StringBuilder("crytpo:coingecko:coin-markets:")
    //           .append(currency).append(":").append(id).toString();
    //       value = redisService.getValue(key);
    //       market = objectMapper.readValue(value, Market.class);
    //       markets.add(market);
    //     }
    //   }

    //   return ApiResponse.<List<Market>>builder() //
    //       .ok() //
    //       .data(markets) //
    //       .build();
    // }

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
