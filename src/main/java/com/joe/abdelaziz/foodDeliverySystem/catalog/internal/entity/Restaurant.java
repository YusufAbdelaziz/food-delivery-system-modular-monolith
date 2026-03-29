package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Menu;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Restaurant extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;

  private String name;

  private String imageUrl;

  private BigDecimal avgRating;

  private int successfulOrders;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "menu_id")
  @JsonIgnoreProperties("restaurant")
  private Menu menu;

  @Column(name = "address_id")
  private Long addressId;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
  @JsonIgnoreProperties("restaurant")
  private Set<RestaurantCuisine> cuisines = new HashSet<>();

  private boolean visible;
}







