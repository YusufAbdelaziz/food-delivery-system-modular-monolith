package com.joe.abdelaziz.foodDeliverySystem.notification.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.NotificationStatus;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.event.NotificationCreatedEvent;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory.NotificationGenerationStrategyFactory;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationRepository;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.service.NotificationListener;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.NotificationGenerationStrategy;

@ExtendWith(MockitoExtension.class)
class NotificationListenerTest {

  @Mock
  private NotificationRepository notificationRepository;

  @Mock
  private NotificationGenerationStrategyFactory strategyFactory;

  @Mock
  private NotificationGenerationStrategy strategy;

  @InjectMocks
  private NotificationListener notificationProcessor;

  @Captor
  private ArgumentCaptor<Notification> notificationCaptor;

  @Test
  void shouldMarkNotificationAsSentWhenProcessingSucceeds() {
    Notification notification = new Notification();
    notification.setId(11L);
    notification.setStatus(NotificationStatus.PENDING);

    NotificationCreatedEvent event = new NotificationCreatedEvent(
        11L,
        99L,
        ChannelType.SMS,
        "message",
        null);

    when(notificationRepository.findById(11L)).thenReturn(Optional.of(notification));
    when(strategyFactory.getStrategy(ChannelType.SMS)).thenReturn(strategy);

    notificationProcessor.onNotificationCreated(event);

    verify(notificationRepository).save(notificationCaptor.capture());
    assertEquals(NotificationStatus.SENT, notificationCaptor.getValue().getStatus());
  }

  @Test
  void shouldMarkNotificationAsFailedWhenDispatchThrows() {
    Notification notification = new Notification();
    notification.setId(12L);
    notification.setStatus(NotificationStatus.PENDING);

    NotificationCreatedEvent event = new NotificationCreatedEvent(
        12L,
        99L,
        ChannelType.WHATSAPP,
        "message",
        null);

    when(notificationRepository.findById(12L)).thenReturn(Optional.of(notification));
    when(strategyFactory.getStrategy(ChannelType.WHATSAPP)).thenReturn(strategy);
    org.mockito.Mockito.doThrow(new RuntimeException("dispatch failed"))
        .when(strategy)
        .execute(any(Notification.class), isNull());

    notificationProcessor.onNotificationCreated(event);

    verify(notificationRepository).save(notificationCaptor.capture());
    assertEquals(NotificationStatus.FAILED, notificationCaptor.getValue().getStatus());
  }
}
