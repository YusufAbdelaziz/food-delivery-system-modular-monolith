package com.joe.abdelaziz.foodDeliverySystem.orders.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joe.abdelaziz.foodDeliverySystem.common.validation.NullableRating;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDTO {
  private Long id;

  @NotNull
  private Long customerId;

  private Long promotionId;
  private String promotionCode;

  @NotNull
  private Set<OrderRestaurantDTO> restaurants = new HashSet<>();

  private Long courierId;

  private BigDecimal totalDeliveryFees = BigDecimal.ZERO;

  private BigDecimal discountedTotalDeliveryFees;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
  private LocalDateTime estimatedDeliveryDate;

  private BigDecimal orderTotal = BigDecimal.ZERO;

  private BigDecimal discountedOrderTotal;

  @NullableRating
  private Byte courierRating;

  @Size(max = 2000, message = "Courier feedback should be at least 2000 characters in length")
  private String courierFeedback;

  private OrderStatus status;
}
