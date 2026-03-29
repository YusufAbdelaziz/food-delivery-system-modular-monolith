package com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationDeserializer;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationSerializer;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.Data;

@Data
public class RestaurantDeliveryFeeDTO {
  private Long id;

  private BigDecimal currentPrice;

  private BigDecimal discountedPrice;

  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;

  private Long toRegionId;

  private Long fromRegionId;

  private Long orderRestaurantId;
}








