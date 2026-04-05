package com.joe.abdelaziz.foodDeliverySystem.notification.api.command;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublishNotificationCommand {
  private Long userId;
  private ChannelType channelType;
  private String message;
  private PublishNotificationRecipient recipientInfo;
  private OrderDTO order;
}
