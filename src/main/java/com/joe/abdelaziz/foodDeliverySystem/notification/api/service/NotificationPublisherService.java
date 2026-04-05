package com.joe.abdelaziz.foodDeliverySystem.notification.api.service;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationCommand;

public interface NotificationPublisherService {
  Long publish(PublishNotificationCommand command);
}
