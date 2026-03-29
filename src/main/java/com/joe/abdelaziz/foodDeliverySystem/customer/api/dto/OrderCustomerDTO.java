package com.joe.abdelaziz.foodDeliverySystem.customer.api.dto;

import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableEmail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderCustomerDTO {
  @NotNull private Long id;

  @NotBlank
  @Size(min = 11)
  private String phoneNumber;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @NullableEmail
  @Column(unique = true)
  private String email;

  private String imageUrl;

  @NotNull private Long activeAddressId;
}








