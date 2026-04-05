package com.joe.abdelaziz.foodDeliverySystem.notification.internal.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationCommand;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationRecipient;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.NotificationStatus;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.event.NotificationCreatedEvent;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.service.NotificationPublisherService;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.NotificationChannel;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory.NotificationGenerationStrategyFactory;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationChannelRepository;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository.NotificationRepository;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.NotificationGenerationStrategy;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationPublisherServiceImpl implements NotificationPublisherService {

  private final NotificationRepository notificationRepository;
  private final NotificationChannelRepository notificationChannelRepository;
  private final NotificationGenerationStrategyFactory strategyFactory;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public Long publish(PublishNotificationCommand command) {
    if (command == null) {
      throw new BusinessLogicException("Notification command is required");
    }

    ChannelType channelType = command.getChannelType();
    if (channelType == null) {
      throw new BusinessLogicException("Notification channel type is required");
    }

    NotificationGenerationStrategy strategy = strategyFactory.getStrategy(channelType);
    RecipientInfo normalizedRecipientInfo = strategy.generate(toRecipientInfo(command.getRecipientInfo()));
    NotificationChannel channel = getActiveChannel(channelType);

    Notification notification = new Notification();
    notification.setUserId(command.getUserId());
    notification.setChannel(channel);
    notification.setMessage(command.getMessage());
    notification.setRecipientInfo(normalizedRecipientInfo);
    notification.setStatus(NotificationStatus.PENDING);

    Notification savedNotification = notificationRepository.save(notification);

    eventPublisher.publishEvent(
        new NotificationCreatedEvent(
            savedNotification.getId(),
            savedNotification.getUserId(),
            channelType,
            savedNotification.getMessage(),
            command.getOrder()));

    return savedNotification.getId();
  }

  private RecipientInfo toRecipientInfo(PublishNotificationRecipient commandRecipientInfo) {
    if (commandRecipientInfo == null) {
      return null;
    }

    return RecipientInfo.builder()
        .email(commandRecipientInfo.getEmail())
        .phone(commandRecipientInfo.getPhone())
        .build();
  }

  private NotificationChannel getActiveChannel(ChannelType channelType) {
    return notificationChannelRepository.findByChannelTypeAndIsActiveTrue(channelType)
        .orElseGet(
            () -> {
              if (notificationChannelRepository.findByChannelType(channelType).isPresent()) {
                throw new BusinessLogicException(
                    String.format("Notification channel %s is inactive", channelType));
              }
              throw new RecordNotFoundException(
                  String.format("Notification channel %s is not configured", channelType));
            });
  }
}
