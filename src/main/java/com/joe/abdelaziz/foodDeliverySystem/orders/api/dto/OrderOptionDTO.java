package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderOptionDTO {
  private Long id;

  @NotBlank private String name;

  @PositiveOrZero private BigDecimal price;
}








