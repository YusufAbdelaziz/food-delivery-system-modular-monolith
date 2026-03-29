package com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_option_id")
  private Long id;

  // @ManyToOne
  // @JoinColumn(name = "order_spec_id")
  // private OrderSpec orderSpec;

  @NotBlank private String name;

  @PositiveOrZero private BigDecimal price;
}







