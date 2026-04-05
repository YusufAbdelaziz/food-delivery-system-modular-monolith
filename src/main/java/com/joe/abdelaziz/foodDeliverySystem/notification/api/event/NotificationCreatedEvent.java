package com.joe.abdelaziz.foodDeliverySystem.notification.api.event;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

public record NotificationCreatedEvent(
    Long notificationId,
    Long userId,
    ChannelType channelType,
    String message,
    OrderDTO order) {
}
