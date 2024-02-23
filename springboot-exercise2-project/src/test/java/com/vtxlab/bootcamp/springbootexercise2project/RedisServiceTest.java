package com.vtxlab.bootcamp.springbootexercise2project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import com.vtxlab.bootcamp.springbootexercise2project.Service.impl.RedisService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market.Roi;


// @DataRedisTest
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class RedisServiceTest {

        private Market m1;
        private Market m2;
        private String key1;
        private String key2;
        private String m1Serialized;
        private String m2Serialized;

        @Autowired
        private RedisService redisService;

        // @MockBean
        @SpyBean
        private RedisTemplate<String, String> redisTemplate;

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
                                .athDate(LocalDateTime.parse(
                                                "2021-11-10T14:24:11.849")) //
                                .atl(67.81) //
                                .atlChangePercentage(70818.73563) //
                                .atlDate(LocalDateTime.parse(
                                                "2013-07-06T00:00:00.000")) //
                                .lastUpdated(LocalDateTime.parse(
                                                "2024-02-12T09:05:16.397")) //
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
                                .athDate(LocalDateTime.parse(
                                                "2021-11-10T14:24:19.604")) //
                                .atl(0.432979) //
                                .atlChangePercentage(575146.37857) //
                                .atlDate(LocalDateTime.parse(
                                                "2015-10-20T00:00:00.000")) //
                                .roi(Roi.builder() //
                                                .times(68.1911670378185) //
                                                .currency("btc") //
                                                .percentage(6819.11670378185) //
                                                .build())
                                .lastUpdated(LocalDateTime.parse(
                                                "2024-02-12T09:05:34.169")) //
                                .build();

                this.key1 = "crytpo:coingecko:coin-markets:usd:bitcoin:test";
                this.key2 = "crytpo:coingecko:coin-markets:usd:ethereum:test";

                this.m1Serialized = new StringBuilder("{\"id\":\"bitcoin\",") //
                                .append("\"symbol\":\"btc\",") //
                                .append("\"name\":\"Bitcoin\",") //
                                .append("\"image\":\"https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400\",") //
                                .append("\"current_price\":48119.0,") //
                                .append("\"market_cap\":943979075690,") //
                                .append("\"market_cap_rank\":1,") //
                                .append("\"fully_diluted_valuation\":1010083664413,") //
                                .append("\"total_volume\":14878964982,") //
                                .append("\"high_24h\":48729.0,") //
                                .append("\"low_24h\":47947.0,") //
                                .append("\"price_change_24h\":-91.39385662862333,") //
                                .append("\"price_change_percentage_24h\":-0.18957,") //
                                .append("\"market_cap_change_24h\":-2218967570,") //
                                .append("\"market_change_percentage_24h\":0.0,") //
                                .append("\"circulating_supply24h\":0.0,") //
                                .append("\"total_supply\":2.1E7,") //
                                .append("\"max_supply\":21000000,") //
                                .append("\"ath\":69045.0,") //
                                .append("\"ath_change_percentage\":-30.35057,") //
                                .append("\"ath_date\":\"2021-11-10T14:24:11.849\",") //
                                .append("\"atl\":67.81,") //
                                .append("\"atl_change_percentage\":70818.73563,") //
                                .append("\"atl_date\":\"2013-07-06T00:00:00\",") //
                                .append("\"roi\":null,") //
                                .append("\"last_updated\":\"2024-02-12T09:05:16.397\"}") //
                                .toString();

                this.m2Serialized = new StringBuilder("{\"id\":\"ethereum\",") //
                                .append("\"symbol\":\"eth\",") //
                                .append("\"name\":\"Ethereum\",") //
                                .append("\"image\":\"https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628\",") //
                                .append("\"current_price\":2489.94,") //
                                .append("\"market_cap\":299318023536,") //
                                .append("\"market_cap_rank\":2,") //
                                .append("\"fully_diluted_valuation\":299318023536,") //
                                .append("\"total_volume\":7275066671,") //
                                .append("\"high_24h\":2531.49,") //
                                .append("\"low_24h\":2485.33,") //
                                .append("\"price_change_24h\":-39.68281791189611,") //
                                .append("\"price_change_percentage_24h\":-1.56873,") //
                                .append("\"market_cap_change_24h\":-4226118606,") //
                                .append("\"market_change_percentage_24h\":0.0,") //
                                .append("\"circulating_supply24h\":0.0,") //
                                .append("\"total_supply\":1.20169997442938E8,") //
                                .append("\"max_supply\":0,") //
                                .append("\"ath\":4878.26,") //
                                .append("\"ath_change_percentage\":-48.94296,") //
                                .append("\"ath_date\":\"2021-11-10T14:24:19.604\",") //
                                .append("\"atl\":0.432979,") //
                                .append("\"atl_change_percentage\":575146.37857,") //
                                .append("\"atl_date\":\"2015-10-20T00:00:00\",") //
                                .append("\"roi\":") //
                                .append("{\"times\":68.1911670378185,") //
                                .append("\"currency\":\"btc\",") //
                                .append("\"percentage\":6819.11670378185},") //
                                .append("\"last_updated\":\"2024-02-12T09:05:34.169\"}") //
                                .toString();

        }

        @Test
        void testSetValue() {

                // Mockito.when(redisTemplate.opsForValue().set(key1, m1Serialized)).thenReturn(null);
                // Mockito.when(redisTemplate.opsForValue().get(key1)).thenReturn(m1Serialized);
                String actual = redisService.setValue(key1, m1Serialized);
                assertEquals(m1Serialized, actual);
        }

        @Test
        void testGetValue() {

                // Mockito.when(redisTemplate.opsForValue().get(key1)).thenReturn(m1Serialized);
                redisService.setValue(key1, m1Serialized);
                String actual = redisService.getValue(key1);
                assertEquals(m1Serialized, actual);
        }


}
