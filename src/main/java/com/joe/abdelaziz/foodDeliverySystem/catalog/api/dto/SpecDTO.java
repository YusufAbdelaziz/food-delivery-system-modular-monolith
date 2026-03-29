package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.SpecType;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class SpecDTO {
  private Long id;

  private SpecType type;

  @NotBlank(message = "Spec name should not be blank")
  private String name;

  private Set<SpecOptionDTO> options = new HashSet<>();
}







