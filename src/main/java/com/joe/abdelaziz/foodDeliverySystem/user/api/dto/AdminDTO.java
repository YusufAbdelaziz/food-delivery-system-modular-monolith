package com.joe.abdelaziz.foodDeliverySystem.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminDTO {
  private Long id;

  @NotBlank
  @Size(min = 11)
  private String phoneNumber;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @NullableEmail
  private String email;

  private String imageUrl;

  private Long roleId;
}
