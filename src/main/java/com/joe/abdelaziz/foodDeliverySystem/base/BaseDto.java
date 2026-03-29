package com.joe.abdelaziz.foodDeliverySystem.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseDto<ID> {

  private ID id;

  private boolean isDeleted;

  private String statusCode;

}






