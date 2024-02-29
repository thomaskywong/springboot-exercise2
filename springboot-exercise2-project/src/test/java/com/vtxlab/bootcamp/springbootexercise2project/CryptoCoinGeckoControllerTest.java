package com.vtxlab.bootcamp.springbootexercise2project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.springbootexercise2project.Controller.impl.CryptoCoinGeckoController;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.Service.impl.RedisService;
import com.vtxlab.bootcamp.springbootexercise2project.config.ScheduledConfig;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market.Roi;

@WebMvcTest(CryptoCoinGeckoController.class)
public class CryptoCoinGeckoControllerTest {

    private Market m1;
    private Market m2;
    private String key1;
    private String key2;
    private String m1Serialized;
    private String m2Serialized;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisService redisService;

    // @Autowired
    // @MockBean
    @Mock
    private ObjectMapper objectMapper;

    @MockBean
    private CryptoGeckoService cryptoGeckoService;

    @MockBean
    private ScheduledConfig scheduleConfig;

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

        this.key1 = "crytpo:coingecko:coin-markets:usd:bitcoin";
        this.key2 = "crytpo:coingecko:coin-markets:usd:ethereum";

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

//     @Test
    void testGetMarkets() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime sixtySecondsBeforeNow = nowTime.minusSeconds(60);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtySecondsBeforeNow);

        Mockito.when(objectMapper.writeValueAsString(m1))
                .thenReturn(m1Serialized);
        Mockito.when(objectMapper.writeValueAsString(m2))
                .thenReturn(m2Serialized);

        // String m1Serialized = objectMapper.writeValueAsString(m1);
        // String m2Serialized = objectMapper.writeValueAsString(m2);

        Mockito.when(redisService.getValue(key1)).thenReturn(m1Serialized);
        Mockito.when(redisService.getValue(key2)).thenReturn(m2Serialized);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd")) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(jsonPath("$.code").value("000000")) //
                .andExpect(jsonPath("$.message").value("OK.")) //
                .andExpect(jsonPath("$.data[0].id").value("bitcoin")) //
                .andExpect(jsonPath("$.data[0].symbol").value("btc")) //
                .andExpect(jsonPath("$.data[0].name").value("Bitcoin")) //
                .andExpect(jsonPath("$.data[0].image").value(
                        "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400")) //
                .andExpect(jsonPath("$.data[0].current_price").value(48119.0)) //
                .andExpect(
                        jsonPath("$.data[0].market_cap").value(943979075690L)) //
                .andExpect(jsonPath("$.data[0].market_cap_rank").value(1)) //
                .andExpect(jsonPath("$.data[0].fully_diluted_valuation")
                        .value(1010083664413L)) //
                .andExpect(
                        jsonPath("$.data[0].total_volume").value(14878964982L)) //
                .andExpect(jsonPath("$.data[0].high_24h").value(48729.0)) //
                .andExpect(jsonPath("$.data[0].low_24h").value(47947.0)) //
                .andExpect(jsonPath("$.data[0].price_change_24h")
                        .value(-91.39385662862333)) //
                .andExpect(jsonPath("$.data[0].price_change_percentage_24h")
                        .value(-0.18957)) //
                .andExpect(jsonPath("$.data[0].market_cap_change_24h")
                        .value(-2218967570L)) //
                .andExpect(jsonPath("$.data[0].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[0].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[0].total_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].max_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].ath").value(69045.0)) //
                .andExpect(jsonPath("$.data[0].ath_change_percentage")
                        .value(-30.35057)) //
                .andExpect(jsonPath("$.data[0].ath_date")
                        .value("2021-11-10T14:24:11.849")) //
                .andExpect(jsonPath("$.data[0].atl").value(67.81)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(70818.73563)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2013-07-06T00:00:00")) //
                .andExpect(jsonPath("$.data[0].roi").isEmpty()) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:16.397")) //
                .andExpect(jsonPath("$.data[1].id").value("ethereum")) //
                .andExpect(jsonPath("$.data[1].symbol").value("eth")) //
                .andExpect(jsonPath("$.data[1].name").value("Ethereum")) //
                .andExpect(jsonPath("$.data[1].image").value(
                        "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628")) //
                .andExpect(jsonPath("$.data[1].current_price").value(2489.94)) //
                .andExpect(
                        jsonPath("$.data[1].market_cap").value(299318023536L)) //
                .andExpect(jsonPath("$.data[1].market_cap_rank").value(2)) //
                .andExpect(jsonPath("$.data[1].fully_diluted_valuation")
                        .value(299318023536L)) //
                .andExpect(
                        jsonPath("$.data[1].total_volume").value(7275066671L)) //
                .andExpect(jsonPath("$.data[1].high_24h").value(2531.49)) //
                .andExpect(jsonPath("$.data[1].low_24h").value(2485.33)) //
                .andExpect(jsonPath("$.data[1].price_change_24h")
                        .value(-39.68281791189611)) //
                .andExpect(jsonPath("$.data[1].price_change_percentage_24h")
                        .value(-1.56873)) //
                .andExpect(jsonPath("$.data[1].market_cap_change_24h")
                        .value(-4226118606L)) //
                .andExpect(jsonPath("$.data[1].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[1].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[1].total_supply")
                        .value(120169997.442938)) //
                .andExpect(jsonPath("$.data[1].max_supply").value(0)) //
                .andExpect(jsonPath("$.data[1].ath").value(4878.26)) //
                .andExpect(jsonPath("$.data[1].ath_change_percentage")
                        .value(-48.94296)) //
                .andExpect(jsonPath("$.data[1].ath_date")
                        .value("2021-11-10T14:24:19.604")) //
                .andExpect(jsonPath("$.data[1].atl").value(0.432979)) //
                .andExpect(jsonPath("$.data[1].atl_change_percentage")
                        .value(575146.37857)) //
                .andExpect(jsonPath("$.data[1].atl_date")
                        .value("2015-10-20T00:00:00")) //
                .andExpect(
                        jsonPath("$.data[1].roi.times").value(68.1911670378185)) //
                .andExpect(jsonPath("$.data[1].roi.currency").value("btc")) //
                .andExpect(jsonPath("$.data[1].roi.percentage")
                        .value(6819.11670378185)) //
                .andExpect(jsonPath("$.data[1].last_updated")
                        .value("2024-02-12T09:05:34.169")) //
                .andDo(print());

    }

//     @Test
    void testGetMarketsInvalidCurrency() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime sixtySecondsBeforeNow = nowTime.minusSeconds(60);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtySecondsBeforeNow);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "hkd")) //
                .andExpect(status().isServiceUnavailable()) //
                .andDo(print());

    }

//     @Test
    void testGetMarketsTimeOut() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime sixtyOneSecondsBeforeNow = nowTime.minusSeconds(61);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtyOneSecondsBeforeNow);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd")) //
                .andExpect(status().isServiceUnavailable()) //
                .andDo(print());

    }

//     @Test
    void testGetMarket() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime sixtySecondsBeforeNow = nowTime.minusSeconds(60);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtySecondsBeforeNow);

        Mockito.when(objectMapper.writeValueAsString(m1))
                .thenReturn(m1Serialized);
        Mockito.when(objectMapper.writeValueAsString(m2))
                .thenReturn(m2Serialized);
                
        // String m1Serialized = objectMapper.writeValueAsString(m1);
        // String m2Serialized = objectMapper.writeValueAsString(m2);

        Mockito.when(redisService.getValue(key1)).thenReturn(m1Serialized);
        Mockito.when(redisService.getValue(key2)).thenReturn(m2Serialized);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", "bitcoin", "ethereum")) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(jsonPath("$.code").value("000000")) //
                .andExpect(jsonPath("$.message").value("OK.")) //
                .andExpect(jsonPath("$.data[0].id").value("bitcoin")) //
                .andExpect(jsonPath("$.data[0].symbol").value("btc")) //
                .andExpect(jsonPath("$.data[0].name").value("Bitcoin")) //
                .andExpect(jsonPath("$.data[0].image").value(
                        "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400")) //
                .andExpect(jsonPath("$.data[0].current_price").value(48119.0)) //
                .andExpect(
                        jsonPath("$.data[0].market_cap").value(943979075690L)) //
                .andExpect(jsonPath("$.data[0].market_cap_rank").value(1)) //
                .andExpect(jsonPath("$.data[0].fully_diluted_valuation")
                        .value(1010083664413L)) //
                .andExpect(
                        jsonPath("$.data[0].total_volume").value(14878964982L)) //
                .andExpect(jsonPath("$.data[0].high_24h").value(48729.0)) //
                .andExpect(jsonPath("$.data[0].low_24h").value(47947.0)) //
                .andExpect(jsonPath("$.data[0].price_change_24h")
                        .value(-91.39385662862333)) //
                .andExpect(jsonPath("$.data[0].price_change_percentage_24h")
                        .value(-0.18957)) //
                .andExpect(jsonPath("$.data[0].market_cap_change_24h")
                        .value(-2218967570L)) //
                .andExpect(jsonPath("$.data[0].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[0].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[0].total_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].max_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].ath").value(69045.0)) //
                .andExpect(jsonPath("$.data[0].ath_change_percentage")
                        .value(-30.35057)) //
                .andExpect(jsonPath("$.data[0].ath_date")
                        .value("2021-11-10T14:24:11.849")) //
                .andExpect(jsonPath("$.data[0].atl").value(67.81)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(70818.73563)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2013-07-06T00:00:00")) //
                .andExpect(jsonPath("$.data[0].roi").isEmpty()) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:16.397")) //
                .andExpect(jsonPath("$.data[1].id").value("ethereum")) //
                .andExpect(jsonPath("$.data[1].symbol").value("eth")) //
                .andExpect(jsonPath("$.data[1].name").value("Ethereum")) //
                .andExpect(jsonPath("$.data[1].image").value(
                        "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628")) //
                .andExpect(jsonPath("$.data[1].current_price").value(2489.94)) //
                .andExpect(
                        jsonPath("$.data[1].market_cap").value(299318023536L)) //
                .andExpect(jsonPath("$.data[1].market_cap_rank").value(2)) //
                .andExpect(jsonPath("$.data[1].fully_diluted_valuation")
                        .value(299318023536L)) //
                .andExpect(
                        jsonPath("$.data[1].total_volume").value(7275066671L)) //
                .andExpect(jsonPath("$.data[1].high_24h").value(2531.49)) //
                .andExpect(jsonPath("$.data[1].low_24h").value(2485.33)) //
                .andExpect(jsonPath("$.data[1].price_change_24h")
                        .value(-39.68281791189611)) //
                .andExpect(jsonPath("$.data[1].price_change_percentage_24h")
                        .value(-1.56873)) //
                .andExpect(jsonPath("$.data[1].market_cap_change_24h")
                        .value(-4226118606L)) //
                .andExpect(jsonPath("$.data[1].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[1].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[1].total_supply")
                        .value(120169997.442938)) //
                .andExpect(jsonPath("$.data[1].max_supply").value(0)) //
                .andExpect(jsonPath("$.data[1].ath").value(4878.26)) //
                .andExpect(jsonPath("$.data[1].ath_change_percentage")
                        .value(-48.94296)) //
                .andExpect(jsonPath("$.data[1].ath_date")
                        .value("2021-11-10T14:24:19.604")) //
                .andExpect(jsonPath("$.data[1].atl").value(0.432979)) //
                .andExpect(jsonPath("$.data[1].atl_change_percentage")
                        .value(575146.37857)) //
                .andExpect(jsonPath("$.data[1].atl_date")
                        .value("2015-10-20T00:00:00")) //
                .andExpect(
                        jsonPath("$.data[1].roi.times").value(68.1911670378185)) //
                .andExpect(jsonPath("$.data[1].roi.currency").value("btc")) //
                .andExpect(jsonPath("$.data[1].roi.percentage")
                        .value(6819.11670378185)) //
                .andExpect(jsonPath("$.data[1].last_updated")
                        .value("2024-02-12T09:05:34.169")) //
                .andDo(print());

    }

//     @Test
    void testGetMarketOneId() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime sixtySecondsBeforeNow = nowTime.minusSeconds(60);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtySecondsBeforeNow);

        Mockito.when(objectMapper.writeValueAsString(m1))
                .thenReturn(m1Serialized);
          
        // String m1Serialized = objectMapper.writeValueAsString(m1);

        Mockito.when(redisService.getValue(key1)).thenReturn(m1Serialized);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", "bitcoin")) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(jsonPath("$.code").value("000000")) //
                .andExpect(jsonPath("$.message").value("OK.")) //
                .andExpect(jsonPath("$.data[0].id").value("bitcoin")) //
                .andExpect(jsonPath("$.data[0].symbol").value("btc")) //
                .andExpect(jsonPath("$.data[0].name").value("Bitcoin")) //
                .andExpect(jsonPath("$.data[0].image").value(
                        "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400")) //
                .andExpect(jsonPath("$.data[0].current_price").value(48119.0)) //
                .andExpect(
                        jsonPath("$.data[0].market_cap").value(943979075690L)) //
                .andExpect(jsonPath("$.data[0].market_cap_rank").value(1)) //
                .andExpect(jsonPath("$.data[0].fully_diluted_valuation")
                        .value(1010083664413L)) //
                .andExpect(
                        jsonPath("$.data[0].total_volume").value(14878964982L)) //
                .andExpect(jsonPath("$.data[0].high_24h").value(48729.0)) //
                .andExpect(jsonPath("$.data[0].low_24h").value(47947.0)) //
                .andExpect(jsonPath("$.data[0].price_change_24h")
                        .value(-91.39385662862333)) //
                .andExpect(jsonPath("$.data[0].price_change_percentage_24h")
                        .value(-0.18957)) //
                .andExpect(jsonPath("$.data[0].market_cap_change_24h")
                        .value(-2218967570L)) //
                .andExpect(jsonPath("$.data[0].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[0].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[0].total_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].max_supply").value(21000000)) //
                .andExpect(jsonPath("$.data[0].ath").value(69045.0)) //
                .andExpect(jsonPath("$.data[0].ath_change_percentage")
                        .value(-30.35057)) //
                .andExpect(jsonPath("$.data[0].ath_date")
                        .value("2021-11-10T14:24:11.849")) //
                .andExpect(jsonPath("$.data[0].atl").value(67.81)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(70818.73563)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2013-07-06T00:00:00")) //
                .andExpect(jsonPath("$.data[0].roi").isEmpty()) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:16.397")) //
                .andDo(print());

    }

//     @Test
    void testGetMarketTimeOut() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();

        LocalDateTime sixtyOneSecondsBeforeNow = nowTime.minusSeconds(61);


        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtyOneSecondsBeforeNow);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", "bitcoin")) //
                .andExpect(status().isServiceUnavailable()) //
                .andDo(print());

    }

//     @Test
    void testGetMarketInvalidCurrency() throws Exception {

        LocalDateTime nowTime = LocalDateTime.now();

        LocalDateTime sixtySecondsBeforeNow = nowTime.minusSeconds(60);

        Mockito.when(scheduleConfig.getCoingeckoUpdateTime())
                .thenReturn(sixtySecondsBeforeNow);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", "bitcoin")) //
                .andExpect(status().isServiceUnavailable()) //
                .andDo(print());

    }


}

