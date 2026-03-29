package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class MenuDTO {

  private Long id;

  private RestaurantDTO restaurant;

  private Set<SectionDTO> sections = new HashSet<>();
}







