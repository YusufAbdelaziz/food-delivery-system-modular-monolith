package com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.enums.OrderStatus;
import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableRating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Column(name = "user_id")
  private Long customerId;

  @Column(name = "promotion_id")
  private Long promotionId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private Set<OrderRestaurant> restaurants = new HashSet<>();

  @Column(name = "courier_id")
  private Long courierId;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private BigDecimal totalDeliveryFees;

  private BigDecimal discountedTotalDeliveryFees;

  private BigDecimal discountedOrderTotal;

  private LocalDateTime estimatedDeliveryDate;

  @NullableRating private Byte courierRating;

  @Size(max = 2000, message = "Courier feedback should be at least 2000 characters in length")
  private String courierFeedback;

  private BigDecimal orderTotal = new BigDecimal(0);

  public void addRestaurant(OrderRestaurant restaurant) {
    restaurants.add(restaurant);
    restaurant.setOrder(this);
  }
}








