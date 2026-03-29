package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SpecOptionDTO {

  private Long id;

  @NotBlank
  @Size(min = 2, message = "Option name should be at least 2 characters in length")
  private String name;

  @PositiveOrZero private BigDecimal price;
}







