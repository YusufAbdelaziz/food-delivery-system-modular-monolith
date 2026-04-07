package com.joe.abdelaziz.foodDeliverySystem.notification.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record NotificationOrderSnapshot(
    Long id,
    String status,
    LocalDateTime estimatedDeliveryDate,
    BigDecimal orderTotal,
    BigDecimal totalDeliveryFees,
    List<NotificationLineItemSnapshot> lineItems) {
}
