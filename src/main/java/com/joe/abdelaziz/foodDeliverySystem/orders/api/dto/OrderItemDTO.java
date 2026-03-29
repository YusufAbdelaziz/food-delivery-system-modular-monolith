package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  private int quantity;

  @Positive private BigDecimal price;

  @NotBlank private String name;

  private Set<OrderSpecDTO> specs = new HashSet<>();
}








