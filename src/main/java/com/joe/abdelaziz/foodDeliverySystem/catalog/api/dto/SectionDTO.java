package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class SectionDTO {

  private Long id;

  @NotBlank
  @Size(min = 5, message = "Section name should be at least 5 characters in length")
  private String name;

  @JsonIgnoreProperties("section")
  private Set<ItemDTO> items = new HashSet<>();
}







