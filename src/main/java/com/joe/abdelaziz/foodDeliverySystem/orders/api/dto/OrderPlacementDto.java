package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderPlacementDto {

  @NotNull
  private Long customerId;

  private Long promotionId;

  @NotNull
  private Set<OrderPlacementRestaurantDto> restaurants = new HashSet<>();

  private BigDecimal totalDeliveryFees = BigDecimal.ZERO;

  private BigDecimal discountedTotalDeliveryFees;

  private BigDecimal orderTotal = BigDecimal.ZERO;

  private BigDecimal discountedOrderTotal;
}
