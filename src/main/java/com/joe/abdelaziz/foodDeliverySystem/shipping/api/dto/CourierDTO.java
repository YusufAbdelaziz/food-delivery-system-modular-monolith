package com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class CourierDTO {

  private Long id;

  @NotBlank
  @Size(min = 11)
  private String phoneNumber;

  @NotBlank
  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  private Long roleId;

  private boolean active;

  private BigDecimal avgRating;

  private BigDecimal earnings;

  private int successfulOrders;

  private Set<Long> orderIds = new HashSet<>();

  public void addOrder(Long orderId) {
    orderIds.add(orderId);
  }
}







