package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderPlacementRestaurantDto {

  @Valid
  @NotNull
  private RestaurantDTO restaurant;

  @Valid
  @NotNull
  private Set<OrderItemDTO> items = new HashSet<>();
}
