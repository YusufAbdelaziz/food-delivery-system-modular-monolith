package com.joe.abdelaziz.foodDeliverySystem.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ExistsInTableValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsInTable {
  String message()

  default "Entity does not exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<?> entity();
}









