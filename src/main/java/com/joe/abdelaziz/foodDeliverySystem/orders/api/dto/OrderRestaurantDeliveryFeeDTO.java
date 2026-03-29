package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import java.math.BigDecimal;
import java.time.Duration;
import lombok.Data;

@Data
public class OrderRestaurantDeliveryFeeDTO {
  private Long id;
  private BigDecimal currentPrice;
  private BigDecimal discountedPrice;
  private Duration estimatedDuration;
}
