package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantCuisineDTO {

  @EqualsAndHashCode.Include private RestaurantCuisineIdDTO id;

  @JsonIgnoreProperties({"cuisines", "menu", "address"})
  private RestaurantDTO restaurant;

  @JsonIgnoreProperties("restaurants")
  private CuisineDTO cuisine;
}







