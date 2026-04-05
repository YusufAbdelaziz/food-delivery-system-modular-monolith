package com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.NotificationChannel;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {
  Optional<NotificationChannel> findByChannelTypeAndIsActiveTrue(ChannelType channelType);

  Optional<NotificationChannel> findByChannelType(ChannelType channelType);
}
