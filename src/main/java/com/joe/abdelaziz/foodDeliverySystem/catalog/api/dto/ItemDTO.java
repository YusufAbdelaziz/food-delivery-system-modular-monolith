package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class ItemDTO {

  private Long id;

  @Size(min = 5, message = "Item name should be at least 5 characters in length")
  private String name;

  @Positive private BigDecimal price;

  private Set<SpecDTO> specs = new HashSet<>();
}







