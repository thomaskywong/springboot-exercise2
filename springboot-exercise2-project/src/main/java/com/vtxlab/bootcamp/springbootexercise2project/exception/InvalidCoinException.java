package com.vtxlab.bootcamp.springbootexercise2project.exception;

import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;

public class InvalidCoinException extends IllegalArgumentException {

  public InvalidCoinException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
