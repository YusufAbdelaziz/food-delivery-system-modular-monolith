package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import org.springframework.stereotype.Component;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationOrderSnapshot;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmsNotificationStrategy extends BaseNotificationGenerationStrategy {

  @Override
  public ChannelType channelType() {
    return ChannelType.SMS;
  }

  @Override
  public RecipientInfo generate(RecipientInfo recipientInfo) {
    return requirePhone(recipientInfo, ChannelType.SMS);
  }

  @Override
  public void execute(Notification notification, NotificationOrderSnapshot order) {
    RecipientInfo recipientInfo = requirePhone(notification.getRecipientInfo(), ChannelType.SMS);
    log.info(
        "Sending SMS notification id={} to={} orderId={} message={}",
        notification.getId(),
        recipientInfo.getPhone(),
        order != null ? order.id() : null,
        notification.getMessage());
  }
}
