package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CuisineDTO {

  @EqualsAndHashCode.Include private Long id;

  @NotBlank private String name;

  private String imageUrl;

  @JsonIgnoreProperties({"cuisine"})
  private Set<RestaurantCuisineDTO> restaurants = new HashSet<>();
}







