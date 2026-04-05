package com.joe.abdelaziz.foodDeliverySystem.notification.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
