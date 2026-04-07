package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationOrderSnapshot;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;

public interface NotificationGenerationStrategy {
  ChannelType channelType();

  RecipientInfo generate(RecipientInfo recipientInfo);

  void execute(Notification notification, NotificationOrderSnapshot order);
}
