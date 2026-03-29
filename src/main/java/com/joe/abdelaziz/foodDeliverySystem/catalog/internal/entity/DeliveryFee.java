package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "delivery_fee",
    uniqueConstraints = @UniqueConstraint(columnNames = {"to_region_id", "from_region_id"}))
@Getter
@Setter
public class DeliveryFee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_fee_id")
  private Long id;

  @Column(name = "to_region_id")
  private Long toRegionId;

  @Column(name = "from_region_id")
  private Long fromRegionId;

  private BigDecimal price;

  @Column(name = "estimated_duration_seconds")
  @Convert(converter = DurationConverter.class)
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;
}








