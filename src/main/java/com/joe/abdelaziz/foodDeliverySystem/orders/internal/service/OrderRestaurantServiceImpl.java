package com.joe.abdelaziz.foodDeliverySystem.orders.internal.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderRestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantStatsDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.repository.OrderRestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRestaurantServiceImpl implements OrderRestaurantService {

  private final OrderRestaurantRepository orderRestaurantRepository;

  public List<OrderRestaurantStatsDTO> findRestaurantsAvgRating() {

    return orderRestaurantRepository.findRestaurantsAvgRating();
  }
}







