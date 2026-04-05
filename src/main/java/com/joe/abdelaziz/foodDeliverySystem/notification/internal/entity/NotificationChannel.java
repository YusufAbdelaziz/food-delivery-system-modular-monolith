package com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification_channels")
@Getter
@Setter
public class NotificationChannel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "channel_type", nullable = false, unique = true, length = 20)
  private ChannelType channelType;

  @Column(name = "is_active")
  private boolean isActive = true;
}
