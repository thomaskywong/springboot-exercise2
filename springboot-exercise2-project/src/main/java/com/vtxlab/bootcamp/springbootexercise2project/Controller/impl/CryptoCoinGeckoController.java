package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.springbootexercise2project.Controller.CryptoCoinGeckoOperation;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
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
  private CryptoGeckoService cryptoGeckoService;

  @Override
  public ApiResponse<List<Market>> getMarkets(String currency, String... ids)
      throws Exception {

    if (!(Currency.isValidCurrency(currency))) {
      throw new InvalidCurrencyException(Syscode.INVALID_CURRENCY);
    }

    Currency cur = Currency.toCurrency(currency);

    List<Market> markets = cryptoGeckoService.getMarkets(cur, ids);

      return ApiResponse.<List<Market>>builder() //
          .ok() //
          .data(markets) //
          .build();

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
