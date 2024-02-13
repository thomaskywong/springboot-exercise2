package com.vtxlab.bootcamp.springbootexercise2project.Controller.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vtxlab.bootcamp.springbootexercise2project.Controller.CryptoCoinGeckoOperation;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.exception.InvalidCoinException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;

@RestController
@RequestMapping(value = "/crypto/coingecko/api/v1")
public class CryptoCoinGeckoController implements CryptoCoinGeckoOperation {

  @Autowired
  private CryptoGeckoService crytoGeckoService;

  @Override
  public ApiResponse<List<Market>> getMarkets(String currency, String... ids) {
    
    // System.out.println(Arrays.toString(ids));

    Currency cur = Currency.toCurrency(currency);

    if (ids == null) {
      return ApiResponse.<List<Market>>builder() //
          .code(Syscode.OK.getCode()) //
          .message(Syscode.OK.getMessage()) //
          .data(crytoGeckoService.getMarkets(cur)) //
          .build();
    } else {
      List<Coin> coins = crytoGeckoService.getCoins();

      for(String id : ids) {
        if(!(Coin.isValidCoin(coins, id))) {
          throw new InvalidCoinException(Syscode.INVALID_COIN);
        }
      }

      return ApiResponse.<List<Market>>builder() //
          .code(Syscode.OK.getCode()) //
          .message(Syscode.OK.getMessage()) //
          .data(crytoGeckoService.getMarkets(cur, ids)) //
          .build();
    }

  }

}
