package com.joe.abdelaziz.foodDeliverySystem.orders.api.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.event.OrderPlacedEvent;

public interface OrderEventPublisher {
  void publishOrderPlaced(OrderPlacedEvent event);
}
