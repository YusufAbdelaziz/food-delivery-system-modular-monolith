package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderItemDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderOptionDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderSpecDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemsFlattenUseCase {

  public List<EmailOrderLineItemView> flatten(Set<OrderRestaurantDTO> restaurants) {
    if (restaurants == null || restaurants.isEmpty()) {
      return List.of();
    }

    List<EmailOrderLineItemView> items = new ArrayList<>();

    for (OrderRestaurantDTO restaurant : restaurants) {
      if (restaurant == null || restaurant.getItems() == null) {
        continue;
      }

      for (OrderItemDTO item : restaurant.getItems()) {
        if (item == null) {
          continue;
        }

        items.add(
            new EmailOrderLineItemView(
                item.getName() != null ? item.getName() : "Unnamed item",
                item.getQuantity(),
                item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO,
                calculateOptionTotal(item.getSpecs())));
      }
    }

    return items;
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
