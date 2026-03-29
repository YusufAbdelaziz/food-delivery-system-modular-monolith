package com.joe.abdelaziz.foodDeliverySystem.iam.api.dto;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import lombok.Data;

@Data
public class RoleDTO {

  private Long id;

  private RoleType type;
}







