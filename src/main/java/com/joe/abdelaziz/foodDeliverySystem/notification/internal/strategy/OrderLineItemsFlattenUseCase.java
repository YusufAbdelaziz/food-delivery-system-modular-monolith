package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.notification.api.model.NotificationLineItemSnapshot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemsFlattenUseCase {

  public List<EmailOrderLineItemView> flatten(List<NotificationLineItemSnapshot> lineItems) {
    if (lineItems == null || lineItems.isEmpty()) {
      return List.of();
    }

    List<EmailOrderLineItemView> items = new ArrayList<>();

    for (NotificationLineItemSnapshot lineItem : lineItems) {
      if (lineItem == null) {
        continue;
      }

      items.add(
          new EmailOrderLineItemView(
              lineItem.name() != null ? lineItem.name() : "Unnamed item",
              lineItem.quantity() != null ? lineItem.quantity() : 0,
              lineItem.unitPrice() != null ? lineItem.unitPrice() : java.math.BigDecimal.ZERO,
              lineItem.optionsTotal() != null ? lineItem.optionsTotal() : java.math.BigDecimal.ZERO));
    }

    return items;
  }
}
