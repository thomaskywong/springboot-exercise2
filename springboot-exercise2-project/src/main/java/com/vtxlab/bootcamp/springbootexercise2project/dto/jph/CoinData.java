package com.vtxlab.bootcamp.springbootexercise2project.dto.jph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class CoinData {

  private String id;
  private String image;
  
  @JsonProperty(value = "current_price")
  private Double currentPrice;

}
