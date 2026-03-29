package com.joe.abdelaziz.foodDeliverySystem.iam.api.auth;

import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableEmail;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank
  @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
  @Size(min = 11)
  private String phoneNumber;

  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @NullableEmail
  private String email;

  @NotNull
  private RoleType role;
}
