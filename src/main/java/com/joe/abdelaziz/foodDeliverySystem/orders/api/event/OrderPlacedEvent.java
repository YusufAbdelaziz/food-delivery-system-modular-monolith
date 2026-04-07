package com.joe.abdelaziz.foodDeliverySystem.orders.api.event;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

public record OrderPlacedEvent(
    Long customerId,
    String customerEmail,
    String customerPhone,
    OrderDTO order) {
}
