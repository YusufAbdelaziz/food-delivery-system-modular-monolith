package com.joe.abdelaziz.foodDeliverySystem.notification.api.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublishNotificationRecipient {
  private String email;
  private String phone;
}
