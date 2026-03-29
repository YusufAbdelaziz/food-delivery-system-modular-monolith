package com.joe.abdelaziz.foodDeliverySystem.iam.api.auth;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  private String phoneNumber;
  private String password;
  private RoleType role;
}
