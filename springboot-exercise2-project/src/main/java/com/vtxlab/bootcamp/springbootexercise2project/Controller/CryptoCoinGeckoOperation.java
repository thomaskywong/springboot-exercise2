package com.vtxlab.bootcamp.springbootexercise2project.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;

public interface CryptoCoinGeckoOperation {

    @GetMapping(value = "/coins/markets")
    @ResponseStatus(value = HttpStatus.OK)
    @CrossOrigin // For ApiResponse unwrapping
    ApiResponse<List<Market>> getMarkets(
            @RequestParam(name = "currency", required = true) String currency, //
            @RequestParam(name = "ids", required = false) String... ids) //
            throws Exception;

    @GetMapping(value = "/coins/list")
    @ResponseStatus(value = HttpStatus.OK)
    ApiResponse<List<Coin>> getCoins() throws JsonProcessingException;

    @GetMapping(value = "/coins-price")
    ModelAndView showCoins();

    @GetMapping(value = "/coins-data")
    String showCoins(Model model);


}
