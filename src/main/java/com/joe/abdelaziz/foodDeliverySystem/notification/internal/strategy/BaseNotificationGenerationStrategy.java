package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

abstract class BaseNotificationGenerationStrategy implements NotificationGenerationStrategy {

  @Override
  public abstract void execute(Notification notification, OrderDTO order);

  protected RecipientInfo requireEmail(RecipientInfo recipientInfo, ChannelType channelType) {
    if (recipientInfo == null) {
      throw new BusinessLogicException(
          String.format("Recipient info is required for channel %s", channelType));
    }

    String recipientValue = recipientInfo.getEmail();
    if (recipientValue == null || recipientValue.isBlank()) {
      throw new BusinessLogicException(
          String.format("Recipient key 'email' is required for channel %s", channelType));
    }

    return RecipientInfo.builder()
        .email(recipientValue.trim())
        .build();
  }

  protected RecipientInfo requirePhone(RecipientInfo recipientInfo, ChannelType channelType) {
    if (recipientInfo == null) {
      throw new BusinessLogicException(
          String.format("Recipient info is required for channel %s", channelType));
    }

    String recipientValue = recipientInfo.getPhone();
    if (recipientValue == null || recipientValue.isBlank()) {
      throw new BusinessLogicException(
          String.format("Recipient key 'phone' is required for channel %s", channelType));
    }

    return RecipientInfo.builder()
        .phone(recipientValue.trim())
        .build();
  }
}
