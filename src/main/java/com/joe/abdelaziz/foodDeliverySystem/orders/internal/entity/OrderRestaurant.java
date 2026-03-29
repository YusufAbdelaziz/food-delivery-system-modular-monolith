package com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joe.abdelaziz.foodDeliverySystem.base.BaseEntity;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantStatsDTO;
import com.joe.abdelaziz.foodDeliverySystem.common.converters.DurationConverter;
import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableRating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@SqlResultSetMapping(
    name = "OrderRestaurantStatsDTOMapping",
    classes =
        @ConstructorResult(
            targetClass = OrderRestaurantStatsDTO.class,
            columns = {
              @ColumnResult(name = "restaurantId", type = Long.class),
              @ColumnResult(name = "avgRating", type = BigDecimal.class)
            }))
@NamedNativeQuery(
    name = "OrderRestaurant.findRestaurantsAvgRating",
    query =
        "SELECT r.restaurant_id AS restaurantId, AVG(r.restaurant_rating) AS avgRating "
            + "FROM order_restaurant r GROUP BY r.restaurant_id",
    resultSetMapping = "OrderRestaurantStatsDTOMapping")
/**
 * Represents an order restaurant entity in the database. This entity represents the many-to-many
 * relationship between orders and restaurants.
 */
@Entity
@Table(name = "order_restaurant")
@Getter
@Setter
public class OrderRestaurant extends BaseEntity {

  /** The unique identifier of the order restaurant. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_restaurant_id")
  private Long id;

  /** The order associated with this order restaurant. */
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private Order order;

  @NullableRating private Byte restaurantRating;

  @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
  private String restaurantFeedback;

  @Column(name = "restaurant_id")
  private Long restaurantId;

  /** The order items associated with this order restaurant. */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_restaurant_id")
  private Set<OrderItem> items = new HashSet<>();

  @Column(name = "delivery_fee_current_price")
  private BigDecimal deliveryFeeCurrentPrice;

  @Column(name = "delivery_fee_discounted_price")
  private BigDecimal deliveryFeeDiscountedPrice;

  @Column(name = "estimated_delivery_duration_seconds")
  @Convert(converter = DurationConverter.class)
  private Duration estimatedDeliveryDuration;

  private BigDecimal totalRestaurantReceipt;

  private BigDecimal discountedTotalRestaurantReceipt;

  public void addItem(OrderItem item) {
    items.add(item);
  }
}








