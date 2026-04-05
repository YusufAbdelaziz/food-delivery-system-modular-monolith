package com.joe.abdelaziz.foodDeliverySystem.notification.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationCommand;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationRecipient;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.NotificationStatus;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.event.NotificationCreatedEvent;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.NotificationChannel;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory.NotificationGenerationStrategyFactory;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationChannelRepository;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationRepository;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.NotificationGenerationStrategy;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

@ExtendWith(MockitoExtension.class)
class NotificationPublisherServiceImplTest {

  @Mock
  private NotificationRepository notificationRepository;

  @Mock
  private NotificationChannelRepository notificationChannelRepository;

  @Mock
  private NotificationGenerationStrategyFactory strategyFactory;

  @Mock
  private NotificationGenerationStrategy strategy;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @InjectMocks
  private NotificationPublisherServiceImpl service;

  @Captor
  private ArgumentCaptor<Notification> notificationCaptor;

  @Captor
  private ArgumentCaptor<NotificationCreatedEvent> eventCaptor;

  private NotificationChannel activeEmailChannel;

  @BeforeEach
  void setup() {
    activeEmailChannel = new NotificationChannel();
    activeEmailChannel.setId(1L);
    activeEmailChannel.setChannelType(ChannelType.EMAIL);
    activeEmailChannel.setActive(true);
  }

  @Test
  void shouldPublishAndPersistPendingNotification() {
    RecipientInfo recipientInfo = RecipientInfo.builder().email("user@example.com").build();
    OrderDTO order = new OrderDTO();
    order.setId(555L);
    PublishNotificationCommand command = PublishNotificationCommand.builder()
        .userId(7L)
        .channelType(ChannelType.EMAIL)
        .message("Your order has been accepted")
        .recipientInfo(
            PublishNotificationRecipient.builder()
                .email(recipientInfo.getEmail())
                .build())
        .order(order)
        .build();

    when(strategyFactory.getStrategy(ChannelType.EMAIL)).thenReturn(strategy);
    when(strategy.generate(recipientInfo)).thenReturn(recipientInfo);
    when(notificationChannelRepository.findByChannelTypeAndIsActiveTrue(ChannelType.EMAIL))
        .thenReturn(Optional.of(activeEmailChannel));
    when(notificationRepository.save(any(Notification.class)))
        .thenAnswer(
            invocation -> {
              Notification notification = invocation.getArgument(0);
              notification.setId(100L);
              return notification;
            });

    Long generatedId = service.publish(command);

    assertEquals(100L, generatedId);
    verify(notificationRepository).save(notificationCaptor.capture());
    Notification savedNotification = notificationCaptor.getValue();
    assertEquals(7L, savedNotification.getUserId());
    assertEquals(NotificationStatus.PENDING, savedNotification.getStatus());
    assertEquals(activeEmailChannel, savedNotification.getChannel());
    assertEquals(recipientInfo, savedNotification.getRecipientInfo());

    verify(eventPublisher).publishEvent(eventCaptor.capture());
    NotificationCreatedEvent event = eventCaptor.getValue();
    assertEquals(100L, event.notificationId());
    assertEquals(ChannelType.EMAIL, event.channelType());
    assertEquals(7L, event.userId());
    assertEquals(order, event.order());
  }

  @Test
  void shouldFailWhenChannelIsInactive() {
    RecipientInfo recipientInfo = RecipientInfo.builder().email("user@example.com").build();
    PublishNotificationCommand command = PublishNotificationCommand.builder()
        .userId(7L)
        .channelType(ChannelType.EMAIL)
        .message("Your order has been accepted")
        .recipientInfo(
            PublishNotificationRecipient.builder()
                .email(recipientInfo.getEmail())
                .build())
        .build();

    NotificationChannel inactiveEmailChannel = new NotificationChannel();
    inactiveEmailChannel.setId(2L);
    inactiveEmailChannel.setChannelType(ChannelType.EMAIL);
    inactiveEmailChannel.setActive(false);

    when(strategyFactory.getStrategy(ChannelType.EMAIL)).thenReturn(strategy);
    when(strategy.generate(recipientInfo)).thenReturn(recipientInfo);
    when(notificationChannelRepository.findByChannelTypeAndIsActiveTrue(ChannelType.EMAIL))
        .thenReturn(Optional.empty());
    when(notificationChannelRepository.findByChannelType(ChannelType.EMAIL))
        .thenReturn(Optional.of(inactiveEmailChannel));

    assertThrows(
        BusinessLogicException.class,
        () -> service.publish(command));

    verify(notificationRepository, never()).save(any(Notification.class));
    verify(eventPublisher, never()).publishEvent(any());
  }
}
