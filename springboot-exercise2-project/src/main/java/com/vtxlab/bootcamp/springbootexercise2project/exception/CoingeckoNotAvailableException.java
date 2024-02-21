package com.vtxlab.bootcamp.springbootexercise2project.exception;

import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;

public class CoingeckoNotAvailableException extends RestClientException {

  public CoingeckoNotAvailableException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
