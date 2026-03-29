package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderRestaurantStatsDTO {
  private Long restaurantId;
  private BigDecimal avgRating;
}








