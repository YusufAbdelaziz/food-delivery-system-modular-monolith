package com.joe.abdelaziz.foodDeliverySystem.common.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

  @Bean
  public CycleAvoidingMappingContext cycleAvoidingMappingContext() {
    return new CycleAvoidingMappingContext();
  }
}









