package com.joe.abdelaziz.foodDeliverySystem.notification.internal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationCommand;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.command.PublishNotificationRecipient;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationLineItemSnapshot;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationOrderSnapshot;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.service.NotificationPublisherService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderOptionDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderSpecDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.event.OrderPlacedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderPlacedEventListener {

  private final NotificationPublisherService notificationPublisherService;

  @ApplicationModuleListener
  public void onOrderPlaced(OrderPlacedEvent event) {
    if (event == null) {
      return;
    }

    OrderDTO order = event.order();

    notificationPublisherService.publish(
        PublishNotificationCommand.builder()
            .userId(event.customerId())
            .channelType(ChannelType.EMAIL)
            .recipientInfo(PublishNotificationRecipient.builder()
                .email(event.customerEmail())
                .phone(event.customerPhone())
                .build())
            .message(resolveMessage(order))
            .order(toNotificationOrderSnapshot(order))
            .build());
  }

  private String resolveMessage(OrderDTO order) {
    if (order != null && order.getId() != null) {
      return String.format("Your order with id %d has been placed successfully!", order.getId());
    }
    return "Your order has been placed successfully!";
  }

  private NotificationOrderSnapshot toNotificationOrderSnapshot(OrderDTO order) {
    if (order == null) {
      return null;
    }

    return NotificationOrderSnapshot.builder()
        .id(order.getId())
        .status(order.getStatus() != null ? order.getStatus().name() : null)
        .estimatedDeliveryDate(order.getEstimatedDeliveryDate())
        .orderTotal(order.getOrderTotal())
        .totalDeliveryFees(order.getTotalDeliveryFees())
        .lineItems(toNotificationLineItemSnapshots(order.getRestaurants()))
        .build();
  }

  private List<NotificationLineItemSnapshot> toNotificationLineItemSnapshots(
      Set<OrderRestaurantDTO> restaurants) {
    if (restaurants == null || restaurants.isEmpty()) {
      return List.of();
    }

    return restaurants.stream()
        .filter(restaurant -> restaurant != null && restaurant.getItems() != null)
        .flatMap(restaurant -> restaurant.getItems().stream())
        .filter(item -> item != null)
        .map(item -> NotificationLineItemSnapshot.builder()
            .name(item.getName())
            .quantity(item.getQuantity())
            .unitPrice(item.getPrice())
            .optionsTotal(calculateOptionTotal(item.getSpecs()))
            .build())
        .toList();
  }

  private BigDecimal calculateOptionTotal(Set<OrderSpecDTO> specs) {
    if (specs == null || specs.isEmpty()) {
      return BigDecimal.ZERO;
    }

    BigDecimal total = BigDecimal.ZERO;
    for (OrderSpecDTO spec : specs) {
      if (spec == null || spec.getOptions() == null) {
        continue;
      }
      for (OrderOptionDTO option : spec.getOptions()) {
        if (option != null && option.getPrice() != null) {
          total = total.add(option.getPrice());
        }
      }
    }
    return total;
  }
}
