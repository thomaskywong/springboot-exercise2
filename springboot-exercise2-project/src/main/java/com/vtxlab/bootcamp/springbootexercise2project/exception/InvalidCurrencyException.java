package com.vtxlab.bootcamp.springbootexercise2project.exception;

import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;

public class InvalidCurrencyException extends IllegalArgumentException {

  public InvalidCurrencyException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
