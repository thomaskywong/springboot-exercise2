package com.vtxlab.bootcamp.springbootexercise2project.infra;

import lombok.Getter;

@Getter
public enum Syscode {

  OK("000000", "OK."), //
  JPH_NOT_AVAILABLE("100001","JsonPlaceHolder API not available."), //
  INVALID_CURRENCY("200001","Invalid Currency."), //
  INVALID_COIN("200002","Invalid Coin Id."), //
  REST_CLIENT_EXEPTION("900001", "RestClient Exception."), //
  NPE_EXCEPTION("900000","Runtime Exception - NPE"), //
  GENERAL_EXCEPTION("999999","Exception"), //
  ;

  private String code;
  private String message;

  private Syscode (String code, String message) {
    this.code = code;
    this.message = message;
  }
  
}
