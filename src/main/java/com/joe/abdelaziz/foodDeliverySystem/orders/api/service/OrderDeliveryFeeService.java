package com.joe.abdelaziz.foodDeliverySystem.orders.api.service;

import org.springframework.lang.NonNull;

import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDeliveryFeeDTO;

public interface OrderDeliveryFeeService {
  OrderRestaurantDeliveryFeeDTO calculateDeliveryFeeForRestaurant(
      @NonNull OrderRestaurantDTO orderRestaurant, @NonNull CustomerDTO customer);
}
