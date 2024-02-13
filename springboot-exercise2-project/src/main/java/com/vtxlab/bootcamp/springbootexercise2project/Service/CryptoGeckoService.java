package com.vtxlab.bootcamp.springbootexercise2project.Service;

import java.util.List;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;

public interface CryptoGeckoService {
  
  List<Coin> getCoins();

  List<Market> getMarkets(Currency currency);

  List<Market> getMarkets(Currency currency, String... ids);

}
