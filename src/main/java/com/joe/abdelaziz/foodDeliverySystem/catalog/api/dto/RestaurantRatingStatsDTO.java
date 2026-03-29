package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRatingStatsDTO {
  private Long restaurantId;
  private BigDecimal avgRating;
}
