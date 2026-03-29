package com.joe.abdelaziz.foodDeliverySystem.shipping.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.common.converters.DurationConverter;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationDeserializer;
import com.joe.abdelaziz.foodDeliverySystem.common.serialization.CustomDurationSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
// Represents the delivery fee of a restaurant in an order.
public class RestaurantDeliveryFee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_delivery_fee_id")
  private Long id;

  @Column(name = "price")
  private BigDecimal currentPrice;

  @Column(name = "to_region_id")
  private Long toRegionId;

  @Column(name = "from_region_id")
  private Long fromRegionId;

  @Column(name = "estimated_duration_seconds")
  @Convert(converter = DurationConverter.class)
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;

  @Column(name = "order_restaurant_id")
  @JsonIgnore
  private Long orderRestaurantId;

  private BigDecimal discountedPrice;
}








