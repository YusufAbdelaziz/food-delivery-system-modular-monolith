package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import java.util.List;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class BuildEmailNotificationContextUseCase {

  private final OrderLineItemsFlattenUseCase orderLineItemsFlattenUseCase;

  public BuildEmailNotificationContextUseCase(
      OrderLineItemsFlattenUseCase orderLineItemsFlattenUseCase) {
    this.orderLineItemsFlattenUseCase = orderLineItemsFlattenUseCase;
  }

  public Context build(Notification notification, OrderDTO order) {
    Context context = new Context();
    context.setVariable("notificationId", notification.getId());
    context.setVariable("message", notification.getMessage() != null ? notification.getMessage() : "");

    if (order == null) {
      context.setVariable("orderId", null);
      context.setVariable("orderStatus", null);
      context.setVariable("estimatedDeliveryDate", null);
      context.setVariable("orderTotal", null);
      context.setVariable("totalDeliveryFees", null);
      context.setVariable("lineItems", List.of());
      return context;
    }

    context.setVariable("orderId", order.getId());
    context.setVariable("orderStatus", order.getStatus());
    context.setVariable("estimatedDeliveryDate", order.getEstimatedDeliveryDate());
    context.setVariable("orderTotal", order.getOrderTotal());
    context.setVariable("totalDeliveryFees", order.getTotalDeliveryFees());
    context.setVariable("lineItems", orderLineItemsFlattenUseCase.flatten(order.getRestaurants()));

    return context;
  }
}
