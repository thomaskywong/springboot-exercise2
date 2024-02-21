package com.vtxlab.bootcamp.springbootexercise2project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.springbootexercise2project.Service.impl.CrytoGeckoServiceImpl;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market.Roi;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Scheme;
import com.vtxlab.bootcamp.springbootexercise2project.model.CryptoGeckoUrlBuilder;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class CryptoCoinGeckoServiceImplTest {

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

    @InjectMocks
    private CrytoGeckoServiceImpl cServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private Market m1;
    private Market m2;
    private Coin c1;
    private Coin c2;

    {
        this.m1 = Market.builder() //
                .id("bitcoin") //
                .symbol("btc") //
                .name("Bitcoin") //
                .image("https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400") //
                .currentPrice(48119.0) //
                .marketCap(943979075690L) //
                .marketCapRank(1) //
                .fullyDilutedValuation(1010083664413L) //
                .totalVolume(14878964982L) //
                .high24h(48729.0) //
                .low24h(47947.0) //
                .priceChange24h(-91.39385662862333) //
                .priceChangePercent24h(-0.18957) //
                .marketCapChange24h(-2218967570L) //
                .marketCapChangePercent24h(0.0) //
                .circulatingSupply(0.0) //
                .totalSupply(21000000) //
                .maxSupply(21000000) //
                .ath(69045.0) //
                .athChangePercentage(-30.35057) //
                .athDate(LocalDateTime.parse("2021-11-10T14:24:11.849")) //
                .atl(67.81) //
                .atlChangePercentage(70818.73563) //
                .atlDate(LocalDateTime.parse("2013-07-06T00:00:00.000")) //
                .lastUpdated(LocalDateTime.parse("2024-02-12T09:05:16.397")) //
                .build();


        this.m2 = Market.builder() //
                .id("ethereum") //
                .symbol("eth") //
                .name("Ethereum") //
                .image("https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628") //
                .currentPrice(2489.94) //
                .marketCap(299318023536L) //
                .marketCapRank(2) //
                .fullyDilutedValuation(299318023536L) //
                .totalVolume(7275066671L) //
                .high24h(2531.49) //
                .low24h(2485.33) //
                .priceChange24h(-39.68281791189611) //
                .priceChangePercent24h(-1.56873) //
                .marketCapChange24h(-4226118606L) //
                .marketCapChangePercent24h(0.0) //
                .circulatingSupply(0.0) //
                .totalSupply(120169997.442938) //
                .maxSupply(0) //
                .ath(4878.26) //
                .athChangePercentage(-48.94296) //
                .athDate(LocalDateTime.parse("2021-11-10T14:24:19.604")) //
                .atl(0.432979) //
                .atlChangePercentage(575146.37857) //
                .atlDate(LocalDateTime.parse("2015-10-20T00:00:00.000")) //
                .roi(Roi.builder() //
                        .times(68.1911670378185) //
                        .currency("btc") //
                        .percentage(6819.11670378185) //
                        .build())
                .lastUpdated(LocalDateTime.parse("2024-02-12T09:05:34.169")) //
                .build();

        this.c1 = Coin.builder() //
                .id("bitcoin") //
                .symbol("btc") //
                .name("Bitcoin") //
                .build();

        this.c2 = Coin.builder() //
                .id("ethereum") //
                .symbol("eth") //
                .name("Ethereum") //
                .build();
    }


    @Test
    @Order(1)
    void testGetCoins() {

        Coin[] coins = new Coin[2];
        coins[0] = c1;
        coins[1] = c2;

        List<Coin> expected = new LinkedList<>();
        expected.add(c1);
        expected.add(c2);

        String urlString = CryptoGeckoUrlBuilder.url(Scheme.HTTPS, domain,
                basepath, endpointCoins, key);

        Mockito.when(restTemplate.getForObject(urlString, Coin[].class)) //
                .thenReturn(coins);

        List<Coin> actual = cServiceImpl.getCoins();

        assertEquals(expected, actual);

    }

    @Test
    @Order(2)
    void testGetMarkets1() {

        Market[] markets = new Market[2];
        markets[0] = m1;
        markets[1] = m2;

        List<Market> expected = new LinkedList<>();
        expected.add(m1);
        expected.add(m2);

        Currency cur = Currency.USD;

        String urlString = CryptoGeckoUrlBuilder.url(Scheme.HTTPS, domain,
                basepath, endpointMarkets, cur, key);

        Mockito.when(restTemplate.getForObject(urlString, Market[].class)) //
                .thenReturn(markets);

        assertEquals(expected, cServiceImpl.getMarkets(cur));
    }

    @Test
    @Order(3)
    void testGetMarkets2() {

        Market[] markets = new Market[2];
        markets[0] = m1;
        markets[1] = m2;

        List<Market> expected = new LinkedList<>();
        expected.add(m1);
        expected.add(m2);

        String[] ids = new String[2];
        ids[0] = "bitcoin";
        ids[1] = "ethereum";

        Currency cur = Currency.USD;

        String urlString = CryptoGeckoUrlBuilder.url(Scheme.HTTPS, domain,
                basepath, endpointMarkets, cur, key, ids);

        Mockito.when(restTemplate.getForObject(urlString, Market[].class)) //
                .thenReturn(markets);

        assertEquals(expected, cServiceImpl.getMarkets(cur, ids));

    }

}
