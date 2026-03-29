package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCuisineId implements Serializable {

  private Long restaurantId;
  private Long cuisineId;
}







