package com.joe.abdelaziz.foodDeliverySystem.location.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddressDTO {
  private Long id;

  private Double latitude;

  private Double longitude;

  private String description;

  private Long customerId;

  private Long restaurantId;

  private Long regionId;

  @NotNull
  @Positive(message = "You should put a positive number as your building number")
  private Short buildingNumber;

  @NotNull
  @Positive(message = "You should put a positive number as your apartment number")
  private Short apartmentNumber;

  @NotNull private Boolean active;
}







