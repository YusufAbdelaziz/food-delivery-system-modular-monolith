package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.UserPromotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "active"})})
@Getter
@Setter
public class Promotion extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "promotion_id")
  private Long id;

  @Size(
      min = 0,
      max = 100,
      message = "Description should be between 0 and 250 characters in length")
  private String description;

  @NotBlank
  @Size(
      min = 3,
      max = 100,
      message = "Promotion code should be between 3 and 100 characters in length")
  private String code;

  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  private BigDecimal discountValue;

  private String headline;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private int maxUsers;

  private int usedCount;

  private boolean active;

  @Column(name = "restaurant_id")
  private Long restaurantId;

  @OneToMany(mappedBy = "promotion")
  @JsonIgnoreProperties("promotion")
  private Set<UserPromotion> userPromotions = new HashSet<>();

}







