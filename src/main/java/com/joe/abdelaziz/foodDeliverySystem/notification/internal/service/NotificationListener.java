package com.joe.abdelaziz.foodDeliverySystem.notification.internal.service;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.NotificationStatus;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.event.NotificationCreatedEvent;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory.NotificationGenerationStrategyFactory;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {
  private final NotificationRepository notificationRepository;
  private final NotificationGenerationStrategyFactory strategyFactory;

  @ApplicationModuleListener
  public void onNotificationCreated(NotificationCreatedEvent event) {
    Notification notification = notificationRepository.findById(event.notificationId())
        .orElseThrow(
            () -> new RecordNotFoundException(
                String.format("Notification id %d is not found", event.notificationId())));

    try {
      strategyFactory.getStrategy(event.channelType()).execute(notification, event.order());
      notification.setStatus(NotificationStatus.SENT);
    } catch (Exception exception) {
      notification.setStatus(NotificationStatus.FAILED);
      log.error("Failed to process notification id={}", event.notificationId(), exception);
    }

    notificationRepository.save(notification);
  }

}
