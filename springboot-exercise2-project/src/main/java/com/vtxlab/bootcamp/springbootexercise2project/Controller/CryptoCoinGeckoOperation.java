package com.vtxlab.bootcamp.springbootexercise2project.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;

public interface CryptoCoinGeckoOperation {

  @GetMapping(value = "/coins")
  @ResponseStatus(value = HttpStatus.OK)
  ApiResponse<List<Market>> getMarkets (
      @RequestParam(name = "currency", required = true) String currency, //
      @RequestParam(name = "ids", required = false) List<String> ids) //
      throws JsonProcessingException;

  @GetMapping(value = "/coins/list")
  @ResponseStatus(value = HttpStatus.OK)
  List<Coin> getCoins();


}
