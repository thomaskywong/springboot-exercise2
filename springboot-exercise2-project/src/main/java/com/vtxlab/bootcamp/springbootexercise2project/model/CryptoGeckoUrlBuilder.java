package com.vtxlab.bootcamp.springbootexercise2project.model;

import org.springframework.beans.factory.annotation.Value;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Currency;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Scheme;
import com.vtxlab.bootcamp.springbootexercise2project.infra.UriCompBuilder;

public class CryptoGeckoUrlBuilder {

  private static final String includePlatform = "?include_platform=false";

  private static final String currencyEqual = "?vs_currency=";

  private static final String apiKeyEqual = "&x_cg_demo_api_key=";

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointCoins, String key) {

    String urlString =
        UriCompBuilder.url(scheme, domain, basepath, endpointCoins);

    StringBuilder urlSB = new StringBuilder(urlString);
    urlSB.append(includePlatform) //
        .append(apiKeyEqual).append(key);

    return urlSB.toString();

  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointMarkets, Currency cur, String key) {

    String urlString =
        UriCompBuilder.url(scheme, domain, basepath, endpointMarkets);

    StringBuilder urlSB = new StringBuilder(urlString);
    urlSB.append(currencyEqual).append(cur.name().toLowerCase())
        .append(apiKeyEqual).append(key);

    return urlSB.toString();

  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointMarkets, Currency cur, String key, String... ids) {

    StringBuilder idsSb = new StringBuilder("&ids=");

    for (int i = 0; i < ids.length; i++) {
      idsSb.append(ids[i]);
      if (i < ids.length - 1) {
        idsSb.append(",");
      }
    }

    String idsEqual = idsSb.toString();

    String urlString =
        UriCompBuilder.url(scheme, domain, basepath, endpointMarkets);

    StringBuilder urlSB = new StringBuilder(urlString);
    urlSB.append(currencyEqual).append(cur.name().toLowerCase()) //
        .append(idsEqual) //
        .append(apiKeyEqual).append(key);

    return urlSB.toString();

  }

}
