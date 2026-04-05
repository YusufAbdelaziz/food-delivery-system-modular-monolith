package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import java.math.BigDecimal;

public record EmailOrderLineItemView(
    String name,
    int quantity,
    BigDecimal basePrice,
    BigDecimal optionsTotal) {
}
