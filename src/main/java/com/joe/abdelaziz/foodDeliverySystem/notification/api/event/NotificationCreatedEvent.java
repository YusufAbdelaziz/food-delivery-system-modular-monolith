package com.joe.abdelaziz.foodDeliverySystem.notification.api.event;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationOrderSnapshot;

public record NotificationCreatedEvent(
    Long notificationId,
    Long userId,
    ChannelType channelType,
    String message,
    NotificationOrderSnapshot order) {
}
