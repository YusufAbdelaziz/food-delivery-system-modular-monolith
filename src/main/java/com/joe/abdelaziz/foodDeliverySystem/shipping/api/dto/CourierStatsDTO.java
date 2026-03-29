package com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierStatsDTO {
  private Long courierId;
  private BigDecimal totalEarning;
  private BigDecimal avgRating;
}







