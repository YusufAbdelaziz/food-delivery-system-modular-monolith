package com.joe.abdelaziz.foodDeliverySystem.common.customResponses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateResponse {
  private String message;
  private Long id;
  private boolean visible;

}








