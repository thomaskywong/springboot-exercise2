package com.vtxlab.bootcamp.springbootexercise2project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.ApiResponse;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidCoinException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> InvalidCoinExceptionHandler(
      InvalidCoinException ex) {
    return ApiResponse.<Void>builder() //
        .status(Syscode.INVALID_COIN) //
        .data(null) //
        .build();
  }

  @ExceptionHandler(InvalidCurrencyException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> InvalidCurrencyExceptionHandler(
      InvalidCurrencyException ex) {
    return ApiResponse.<Void>builder() //
        .status(Syscode.INVALID_CURRENCY) //
        .data(null) //
        .build();
  }

  @ExceptionHandler(CoingeckoNotAvailableException.class)
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
  public ApiResponse<Void> CoingeckoNotAvailableExceptionHandler(
      CoingeckoNotAvailableException ex) {
    return ApiResponse.<Void>builder() //
        .status(Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION) //
        .data(null) //
        .build();
  }

  @ExceptionHandler(RestClientException.class)
  @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
  public ApiResponse<Void> RestClientExceptionHandler(RestClientException ex) {
    return ApiResponse.<Void>builder() //
        .status(Syscode.JPH_NOT_AVAILABLE) //
        .data(null) //
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
  public ApiResponse<Void> ExceptionHandler(Exception ex) {
    return ApiResponse.<Void>builder() //
        .status(Syscode.GENERAL_EXCEPTION) //
        .data(null) //
        .build();
  }


}
