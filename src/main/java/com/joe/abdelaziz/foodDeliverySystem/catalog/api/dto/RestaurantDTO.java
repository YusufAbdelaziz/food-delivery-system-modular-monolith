package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantDTO {
  @EqualsAndHashCode.Include private Long id;

  private String name;

  private String imageUrl;

  private BigDecimal avgRating;

  private int successfulOrders;

  private Long addressId;

  private boolean visible;

  @JsonIgnoreProperties({"restaurant"})
  private Set<RestaurantCuisineDTO> cuisines = new HashSet<>();

  @JsonIgnoreProperties({"restaurant"})
  private MenuDTO menu;
}







