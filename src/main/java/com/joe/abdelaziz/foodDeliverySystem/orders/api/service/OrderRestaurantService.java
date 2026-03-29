package com.joe.abdelaziz.foodDeliverySystem.orders.api.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantStatsDTO;
import java.util.List;

public interface OrderRestaurantService {
  List<OrderRestaurantStatsDTO> findRestaurantsAvgRating();
}




