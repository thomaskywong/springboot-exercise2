package com.vtxlab.bootcamp.springbootexercise2project.infra;

import org.springframework.web.util.UriComponentsBuilder;
import lombok.Getter;

@Getter
public class UriCompBuilder {

  public static String url(Scheme scheme, String domain, String basepath, String endpoint) {
    return UriComponentsBuilder.newInstance() //
        .scheme(scheme.name().toLowerCase()) //
        .host(domain) //
        .path(basepath) //
        .path(endpoint) //
        .toUriString();
  }
  
}
