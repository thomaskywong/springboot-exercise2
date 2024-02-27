package com.vtxlab.bootcamp.springbootexercise2project.Service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.CoinId;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Scheme;
import com.vtxlab.bootcamp.springbootexercise2project.mapper.UriCompBuilder;

@Service
public class CrytoGeckoServiceImpl implements CryptoGeckoService {

        @Value(value = "${api.jph.domain}")
        private String domain;

        @Value(value = "${api.jph.basepath}")
        private String basepath;

        @Value(value = "${api.jph.endpoints.markets}")
        private String endpointMarkets;

        @Value(value = "${api.jph.endpoints.list}")
        private String endpointCoins;

        @Value(value = "${api.jph.key}")
        private String key;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private RedisService redisService;

        @Override
        public List<Market> getMarkets(Currency cur) {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointMarkets, cur, key);

                Market[] markets = restTemplate.getForObject(urlString,
                                Market[].class);

                return Arrays.stream(markets).collect(Collectors.toList());
        }

        @Override
        public List<Market> getMarkets(Currency cur, String... ids) {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointMarkets, cur, key, ids);

                Market[] markets = restTemplate.getForObject(urlString,
                                Market[].class);
                return Arrays.stream(markets).collect(Collectors.toList());

        }

        @Override
        public void getDataToRedis() throws JsonProcessingException {

                // List<Coin> coins = this.getCoins();

                for (Currency cur : Currency.values()) {

                        String currency = cur.name().toLowerCase();

                        for (CoinId coin : CoinId.values()) {
                                String coinId = coin.getId();
                                // String[] ids = new String[1];
                                // ids[0] = coinId;

                                List<Market> markets = this.getMarket(cur, coinId);

                                String key = new StringBuilder(
                                                "crytpo:coingecko:coin-markets:")
                                                                .append(currency)
                                                                .append(":")
                                                                .append(coinId)
                                                                .toString();

                                Market market = markets.get(0);
                                String serializedValue = objectMapper
                                                .writeValueAsString(market);
                                redisService.setValue(key, serializedValue);

                        }

                }

        }

        private List<Market> getMarket(Currency cur, String id) {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointMarkets, cur, key, id);
                
                Market[] market = restTemplate.getForObject(urlString, Market[].class);
                return Arrays.stream(market).collect(Collectors.toList());

        }

        @Override
        public List<Coin> getCoins() {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointCoins, key);

                Coin[] coins = restTemplate.getForObject(urlString,
                                Coin[].class);

                return Arrays.stream(coins).collect(Collectors.toList());
        }



}
