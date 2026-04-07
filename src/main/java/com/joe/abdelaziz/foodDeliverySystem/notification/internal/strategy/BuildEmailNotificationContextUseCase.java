package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationOrderSnapshot;
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

  public Context build(Notification notification, NotificationOrderSnapshot order) {
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

    context.setVariable("orderId", order.id());
    context.setVariable("orderStatus", order.status());
    context.setVariable("estimatedDeliveryDate", order.estimatedDeliveryDate());
    context.setVariable("orderTotal", order.orderTotal());
    context.setVariable("totalDeliveryFees", order.totalDeliveryFees());
    context.setVariable("lineItems", orderLineItemsFlattenUseCase.flatten(order.lineItems()));

    return context;
  }
}
