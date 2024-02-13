package com.vtxlab.bootcamp.springbootexercise2project.Service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Scheme;

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

        private final String includePlatform = "?include_platform=false";
        private final String currencyEqual = "?vs_currency=";

        private final String apiKeyEqual = "&x_cg_demo_api_key=";

        @Value(value = "${api.jph.key}")
        private String key;

        @Autowired
        private RestTemplate restTemplate;

        @Override
        public List<Coin> getCoins() {

                String urlString = UriComponentsBuilder.newInstance() //
                                .scheme(Scheme.HTTPS.name().toLowerCase()) //
                                .host(domain) //
                                .path(basepath) //
                                .path(endpointCoins) //
                                .toUriString();

                urlString = new StringBuilder(urlString).append(includePlatform)
                                .append(apiKeyEqual).append(key).toString();

                Coin[] coins = restTemplate.getForObject(urlString,
                                Coin[].class);

                return Arrays.stream(coins).collect(Collectors.toList());
        }

        @Override
        public List<Market> getMarkets(Currency cur) {

                String urlString = UriComponentsBuilder.newInstance() //
                                .scheme(Scheme.HTTPS.name().toLowerCase()) //
                                .host(domain) //
                                .path(basepath) //
                                .path(endpointMarkets) //
                                .toUriString();

                urlString = new StringBuilder(urlString).append(currencyEqual)
                                .append(cur.name().toLowerCase())
                                .append(apiKeyEqual).append(key).toString();

                Market[] markets = restTemplate.getForObject(urlString,
                                Market[].class);

                return Arrays.stream(markets).collect(Collectors.toList());
        }

        @Override
        public List<Market> getMarkets(Currency cur, String... ids) {

                StringBuilder idsSb = new StringBuilder("&ids=");

                for (int i = 0; i < ids.length; i++) {
                        idsSb.append(ids[i]);
                        if (i < ids.length - 1) {
                                idsSb.append(",");
                        }
                }

                String idsEqual = idsSb.toString();

                String urlString = UriComponentsBuilder.newInstance() //
                                .scheme(Scheme.HTTPS.name().toLowerCase()) //
                                .host(domain) //
                                .path(basepath) //
                                .path(endpointMarkets) //
                                .toUriString();

                urlString = new StringBuilder(urlString) //
                                .append(currencyEqual) //
                                .append(cur.name().toLowerCase()) //
                                .append(idsEqual) //
                                .append(apiKeyEqual) //
                                .append(key) //
                                .toString();

                Market[] markets = restTemplate.getForObject(urlString,
                                Market[].class);
                return Arrays.stream(markets).collect(Collectors.toList());

        }

}
