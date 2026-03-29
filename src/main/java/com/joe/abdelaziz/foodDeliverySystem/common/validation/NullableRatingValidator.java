package com.joe.abdelaziz.foodDeliverySystem.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableRatingValidator implements ConstraintValidator<NullableRating, Byte> {

  @Override
  public boolean isValid(Byte value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return value >= 1 && value <= 5;
  }

}









