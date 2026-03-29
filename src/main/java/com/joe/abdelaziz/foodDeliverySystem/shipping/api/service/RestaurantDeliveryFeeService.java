package com.joe.abdelaziz.foodDeliverySystem.shipping.api.service;

import org.springframework.lang.NonNull;

import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.RestaurantDeliveryFeeDTO;

public interface RestaurantDeliveryFeeService {
  RestaurantDeliveryFeeDTO calculateRestaurantDeliveryFee(
      @NonNull OrderRestaurantDTO orderRestaurant, @NonNull CustomerDTO customer);
}
