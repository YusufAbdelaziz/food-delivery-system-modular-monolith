package com.joe.abdelaziz.foodDeliverySystem.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class BusinessLogicException extends RuntimeException {

  private String message;

  public BusinessLogicException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }
}








