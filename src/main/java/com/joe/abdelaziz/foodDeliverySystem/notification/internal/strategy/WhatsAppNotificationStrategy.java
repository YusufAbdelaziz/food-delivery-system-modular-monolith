package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import org.springframework.stereotype.Component;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WhatsAppNotificationStrategy extends BaseNotificationGenerationStrategy {

  @Override
  public ChannelType channelType() {
    return ChannelType.WHATSAPP;
  }

  @Override
  public RecipientInfo generate(RecipientInfo recipientInfo) {
    return requirePhone(recipientInfo, ChannelType.WHATSAPP);
  }

  @Override
  public void execute(Notification notification, OrderDTO order) {
    RecipientInfo recipientInfo = requirePhone(notification.getRecipientInfo(), ChannelType.WHATSAPP);
    log.info(
        "Sending WHATSAPP notification id={} to={} orderId={} message={}",
        notification.getId(),
        recipientInfo.getPhone(),
        order != null ? order.getId() : null,
        notification.getMessage());
  }
}
