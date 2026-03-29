package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderSpecDTO {

  private Long id;

  private Set<OrderOptionDTO> options = new HashSet<>();

  @NotBlank private String name;
}








