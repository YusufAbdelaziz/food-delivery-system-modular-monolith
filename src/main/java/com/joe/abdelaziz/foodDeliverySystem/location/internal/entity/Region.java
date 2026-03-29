package com.joe.abdelaziz.foodDeliverySystem.location.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Region extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "region_id")
  private Long id;

  @NotBlank
  @Size(max = 100, message = "You should provide a region name of length 100 character at most")
  private String name;

}







