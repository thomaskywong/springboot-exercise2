package com.vtxlab.bootcamp.springbootexercise2project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.infra.RedisHelper;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RedisHelperTest {

  @Autowired
  private RedisHelper redisHelper;

  @MockBean
  private RedisTemplate<String, String> redisTemplate;

  @MockBean
  private ObjectMapper objectMapper;

  private String key1;
  private Market m1;
  private String m1String;

  {
    this.key1 = "crytpo:coingecko:coin-markets:usd:bitcoin";

    this.m1 = Market.builder() //
        .id("bitcoin") //
        .symbol("btc") //
        .name("Bitcoin") //
        .image(
            "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400") //
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

    this.m1String = new StringBuilder("{") //
        .append("\"id\": \"bitcoin\",") //
        .append("\"symbol\": \"btc\",") //
        .append("\"name\": \"Bitcoin\",") //
        .append(
            "\"image\": \"https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400\",") //
        .append("\"current_price\": 51458.0,") //
        .append("\"market_cap\": 1011787699178,") //
        .append("\"market_cap_rank\": 1,") //
        .append("\"fully_diluted_valuation\": 1082201335185,") //
        .append("\"total_volume\": 35268921024,") //
        .append("\"high_24h\": 52902.0,") //
        .append("\"low_24h\": 50953.0,") //
        .append("\"price_change_24h\": -204.39733485293982,") //
        .append("\"price_change_percentage_24h\": -0.39564,") //
        .append("\"market_cap_change_24h\": -3919382951,") //
        .append("\"market_change_percentage_24h\": 0.0,") //
        .append("\"circulating_supply24h\": 0.0,") //
        .append("\"total_supply\": 2.1E7,") //
        .append("\"max_supply\": 21000000,") //
        .append("\"ath\": 69045.0,") //
        .append("\"ath_change_percentage\": -25.16852,") //
        .append("\"ath_date\": \"2021-11-10T14:24:11.849\",") //
        .append("\"atl\": 67.81,") //
        .append("\"atl_change_percentage\": 76095.22496,") //
        .append("\"atl_date\": \"2013-07-06T00:00:00\",") //
        .append("\"roi\": null,") //
        .append("\"last_updated\": \"2024-02-21T09:19:05.378\"}") //
        .toString();


  }

  // public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
  // String value = redisTemplate.opsForValue().get(key);
  // return objectMapper.readValue(value, clazz);
  // }

  // @Test
  void testGet() throws JsonProcessingException {

    Mockito.when(objectMapper.writeValueAsString(m1)).thenReturn(m1String);

    Mockito.when(redisTemplate.opsForValue().get(key1)).thenReturn(m1String);

    Market actual = redisHelper.get(key1, Market.class);

    assertEquals(m1, actual);
  }


}
