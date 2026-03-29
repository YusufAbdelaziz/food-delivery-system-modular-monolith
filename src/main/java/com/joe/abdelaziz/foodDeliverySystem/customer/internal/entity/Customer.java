package com.joe.abdelaziz.foodDeliverySystem.customer.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("USER")
public class Customer extends AppUser {

  @JsonIgnore
  @JsonIgnoreProperties({ "customer" })
  @Column(name = "active_address_id")
  private Long activeAddressId;
}
