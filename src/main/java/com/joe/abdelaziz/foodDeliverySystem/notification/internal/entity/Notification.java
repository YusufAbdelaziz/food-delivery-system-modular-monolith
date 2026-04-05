package com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.NotificationStatus;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.converter.RecipientInfoJsonConverter;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "channel_id", nullable = false)
  private NotificationChannel channel;

  @Column(columnDefinition = "TEXT")
  private String message;

  @Convert(converter = RecipientInfoJsonConverter.class)
  @Column(name = "recipient_info", columnDefinition = "json")
  private RecipientInfo recipientInfo;

  @Enumerated(EnumType.STRING)
  @Column(length = 20, nullable = false)
  private NotificationStatus status;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

}
