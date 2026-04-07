package com.joe.abdelaziz.foodDeliverySystem.orders.internal.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.event.OrderPlacedEvent;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderEventPublisherImpl implements OrderEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publishOrderPlaced(OrderPlacedEvent event) {
    applicationEventPublisher.publishEvent(event);
  }
}
