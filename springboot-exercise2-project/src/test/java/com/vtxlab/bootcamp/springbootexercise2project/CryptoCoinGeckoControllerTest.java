package com.vtxlab.bootcamp.springbootexercise2project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.vtxlab.bootcamp.springbootexercise2project.Controller.impl.CryptoCoinGeckoController;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Coin;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market;
import com.vtxlab.bootcamp.springbootexercise2project.dto.jph.Market.Roi;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;

@WebMvcTest(CryptoCoinGeckoController.class)
public class CryptoCoinGeckoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoGeckoService cryptoGeckoService;

    @Test
    void testGetMarkets() throws Exception {

        Market m1 = Market.builder() //
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
                .athDate("2021-11-10T14:24:11.849Z") //
                .atl(67.81) //
                .atlChangePercentage(70818.73563) //
                .atlDate("2013-07-06T00:00:00.000Z") //
                .lastUpdated("2024-02-12T09:05:16.397Z") //
                .build();

        Market m2 = Market.builder() //
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
                .athDate("2021-11-10T14:24:19.604Z") //
                .atl(0.432979) //
                .atlChangePercentage(575146.37857) //
                .atlDate("2015-10-20T00:00:00.000Z") //
                .roi(Roi.builder() //
                        .times(68.1911670378185) //
                        .currency("btc") //
                        .percentage(6819.11670378185) //
                        .build())
                .lastUpdated("2024-02-12T09:05:34.169Z") //
                .build();

        List<Market> markets = new LinkedList<>();
        markets.add(m1);
        markets.add(m2);

        Mockito.when(cryptoGeckoService.getMarkets(Currency.USD)) //
                .thenReturn(markets);

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
                        .value("2021-11-10T14:24:11.849Z")) //
                .andExpect(jsonPath("$.data[0].atl").value(67.81)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(70818.73563)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2013-07-06T00:00:00.000Z")) //
                .andExpect(jsonPath("$.data[0].roi").isEmpty()) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:16.397Z")) //
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
                        .value("2021-11-10T14:24:19.604Z")) //
                .andExpect(jsonPath("$.data[1].atl").value(0.432979)) //
                .andExpect(jsonPath("$.data[1].atl_change_percentage")
                        .value(575146.37857)) //
                .andExpect(jsonPath("$.data[1].atl_date")
                        .value("2015-10-20T00:00:00.000Z")) //
                .andExpect(
                        jsonPath("$.data[1].roi.times").value(68.1911670378185)) //
                .andExpect(jsonPath("$.data[1].roi.currency").value("btc")) //
                .andExpect(jsonPath("$.data[1].roi.percentage")
                        .value(6819.11670378185)) //
                .andExpect(jsonPath("$.data[1].last_updated")
                        .value("2024-02-12T09:05:34.169Z")) //
                .andDo(print());

    }

    @Test
    void testGetMarkets2() throws Exception {

        Market m2 = Market.builder() //
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
                .athDate("2021-11-10T14:24:19.604Z") //
                .atl(0.432979) //
                .atlChangePercentage(575146.37857) //
                .atlDate("2015-10-20T00:00:00.000Z") //
                .roi(Roi.builder() //
                        .times(68.1911670378185) //
                        .currency("btc") //
                        .percentage(6819.11670378185) //
                        .build())
                .lastUpdated("2024-02-12T09:05:34.169Z") //
                .build();

        List<Market> markets = new LinkedList<>();
        markets.add(m2);

        String[] ids = new String[1];
        ids[0] = "ethereum";

        Mockito.when(cryptoGeckoService.getMarkets(Currency.USD, ids)) //
                .thenReturn(markets);

        Coin coin = Coin.builder() //
                .id("ethereum") //
                .symbol("eth") //
                .name("Ethereum") //
                .build();

        List<Coin> coins = new LinkedList<>(List.of(coin));

        Mockito.when(cryptoGeckoService.getCoins()) //
                .thenReturn(coins);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", ids)) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(jsonPath("$.code").value("000000")) //
                .andExpect(jsonPath("$.message").value("OK.")) //
                .andExpect(jsonPath("$.data[0].id").value("ethereum")) //
                .andExpect(jsonPath("$.data[0].symbol").value("eth")) //
                .andExpect(jsonPath("$.data[0].name").value("Ethereum")) //
                .andExpect(jsonPath("$.data[0].image").value(
                        "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628")) //
                .andExpect(jsonPath("$.data[0].current_price").value(2489.94)) //
                .andExpect(
                        jsonPath("$.data[0].market_cap").value(299318023536L)) //
                .andExpect(jsonPath("$.data[0].market_cap_rank").value(2)) //
                .andExpect(jsonPath("$.data[0].fully_diluted_valuation")
                        .value(299318023536L)) //
                .andExpect(
                        jsonPath("$.data[0].total_volume").value(7275066671L)) //
                .andExpect(jsonPath("$.data[0].high_24h").value(2531.49)) //
                .andExpect(jsonPath("$.data[0].low_24h").value(2485.33)) //
                .andExpect(jsonPath("$.data[0].price_change_24h")
                        .value(-39.68281791189611)) //
                .andExpect(jsonPath("$.data[0].price_change_percentage_24h")
                        .value(-1.56873)) //
                .andExpect(jsonPath("$.data[0].market_cap_change_24h")
                        .value(-4226118606L)) //
                .andExpect(jsonPath("$.data[0].market_change_percentage_24h")
                        .value(0.0)) //
                .andExpect(
                        jsonPath("$.data[0].circulating_supply24h").value(0.0)) //
                .andExpect(jsonPath("$.data[0].total_supply")
                        .value(120169997.442938)) //
                .andExpect(jsonPath("$.data[0].max_supply").value(0)) //
                .andExpect(jsonPath("$.data[0].ath").value(4878.26)) //
                .andExpect(jsonPath("$.data[0].ath_change_percentage")
                        .value(-48.94296)) //
                .andExpect(jsonPath("$.data[0].ath_date")
                        .value("2021-11-10T14:24:19.604Z")) //
                .andExpect(jsonPath("$.data[0].atl").value(0.432979)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(575146.37857)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2015-10-20T00:00:00.000Z")) //
                .andExpect(
                        jsonPath("$.data[0].roi.times").value(68.1911670378185)) //
                .andExpect(jsonPath("$.data[0].roi.currency").value("btc")) //
                .andExpect(jsonPath("$.data[0].roi.percentage")
                        .value(6819.11670378185)) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:34.169Z")) //
                .andDo(print());

    }

    @Test
    void testGetMarkets3() throws Exception {

        Coin c1 = Coin.builder() //
                .id("bitcoin") //
                .symbol("btc") //
                .name("Bitcoin") //
                .build();

        Coin c2 = Coin.builder() //
                .id("ethereum") //
                .symbol("eth") //
                .name("Ethereum") //
                .build();

        List<Coin> coins = new LinkedList<>();
        coins.add(c1);
        coins.add(c2);

        Market m1 = Market.builder() //
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
                .athDate("2021-11-10T14:24:11.849Z") //
                .atl(67.81) //
                .atlChangePercentage(70818.73563) //
                .atlDate("2013-07-06T00:00:00.000Z") //
                .lastUpdated("2024-02-12T09:05:16.397Z") //
                .build();

        Market m2 = Market.builder() //
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
                .athDate("2021-11-10T14:24:19.604Z") //
                .atl(0.432979) //
                .atlChangePercentage(575146.37857) //
                .atlDate("2015-10-20T00:00:00.000Z") //
                .roi(Roi.builder() //
                        .times(68.1911670378185) //
                        .currency("btc") //
                        .percentage(6819.11670378185) //
                        .build())
                .lastUpdated("2024-02-12T09:05:34.169Z") //
                .build();

        List<Market> markets = new LinkedList<>();
        markets.add(m1);
        markets.add(m2);

        String[] ids = new String[2];
        ids[0] = "bitcoin";
        ids[1] = "ethereum";

        Mockito.when(cryptoGeckoService.getMarkets(Currency.USD, ids)) //
                .thenReturn(markets);

        Mockito.when(cryptoGeckoService.getCoins()) //
                .thenReturn(coins);

        mockMvc.perform(get("/crypto/coingecko/api/v1/coins") //
                .param("currency", "usd") //
                .param("ids", ids)) //
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
                        .value("2021-11-10T14:24:11.849Z")) //
                .andExpect(jsonPath("$.data[0].atl").value(67.81)) //
                .andExpect(jsonPath("$.data[0].atl_change_percentage")
                        .value(70818.73563)) //
                .andExpect(jsonPath("$.data[0].atl_date")
                        .value("2013-07-06T00:00:00.000Z")) //
                .andExpect(jsonPath("$.data[0].roi").isEmpty()) //
                .andExpect(jsonPath("$.data[0].last_updated")
                        .value("2024-02-12T09:05:16.397Z")) //
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
                        .value("2021-11-10T14:24:19.604Z")) //
                .andExpect(jsonPath("$.data[1].atl").value(0.432979)) //
                .andExpect(jsonPath("$.data[1].atl_change_percentage")
                        .value(575146.37857)) //
                .andExpect(jsonPath("$.data[1].atl_date")
                        .value("2015-10-20T00:00:00.000Z")) //
                .andExpect(
                        jsonPath("$.data[1].roi.times").value(68.1911670378185)) //
                .andExpect(jsonPath("$.data[1].roi.currency").value("btc")) //
                .andExpect(jsonPath("$.data[1].roi.percentage")
                        .value(6819.11670378185)) //
                .andExpect(jsonPath("$.data[1].last_updated")
                        .value("2024-02-12T09:05:34.169Z")) //
                .andDo(print());

    }


}
