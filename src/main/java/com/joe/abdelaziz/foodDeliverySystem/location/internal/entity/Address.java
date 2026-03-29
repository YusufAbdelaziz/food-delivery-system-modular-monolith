package com.joe.abdelaziz.foodDeliverySystem.location.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long id;

  private Double latitude;

  private Double longitude;

  private String description;

  @Column(name = "user_id")
  private Long customerId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "region_id", nullable = false)
  @JsonIgnoreProperties("addresses")
  private Region region;

  @NotNull
  @Positive(message = "You should put a positive number as your building number")
  private Short buildingNumber;

  @NotNull
  @Positive(message = "You should put a positive number as your apartment number")
  private Short apartmentNumber;

  @NotNull
  @Column(name = "active")
  private Boolean active;
}







