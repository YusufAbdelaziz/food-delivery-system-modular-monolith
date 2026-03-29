package com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationDeserializer;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationSerializer;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.Data;

@Data
public class DeliveryFeeDTO {
  private Long id;

  private Long toRegionId;

  private Long fromRegionId;

  private BigDecimal price;

  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;
}








