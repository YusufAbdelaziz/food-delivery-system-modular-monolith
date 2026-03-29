package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableRating;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRestaurantDTO {

  private Long id;

  private Long restaurantId;

  private Long deliveryFeeId;
  private BigDecimal deliveryFeeCurrentPrice;
  private BigDecimal deliveryFeeDiscountedPrice;
  private Duration estimatedDeliveryDuration;

  private Set<OrderItemDTO> items = new HashSet<>();

  private BigDecimal totalRestaurantReceipt;

  private BigDecimal discountedTotalRestaurantReceipt;

  @NullableRating
  private Byte restaurantRating;

  @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
  private String restaurantFeedback;
}









