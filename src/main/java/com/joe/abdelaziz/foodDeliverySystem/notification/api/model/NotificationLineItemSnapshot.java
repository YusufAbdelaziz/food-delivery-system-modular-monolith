package com.joe.abdelaziz.foodDeliverySystem.notification.api.model;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record NotificationLineItemSnapshot(
    String name,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal optionsTotal) {
}
