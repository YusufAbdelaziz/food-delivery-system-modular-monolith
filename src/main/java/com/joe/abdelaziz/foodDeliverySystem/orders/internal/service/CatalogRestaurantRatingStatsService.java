package com.joe.abdelaziz.foodDeliverySystem.orders.internal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantRatingStatsDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantRatingStatsService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderRestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogRestaurantRatingStatsService implements RestaurantRatingStatsService {

  private final OrderRestaurantService orderRestaurantService;

  @Override
  public List<RestaurantRatingStatsDTO> findRestaurantsAvgRating() {
    return orderRestaurantService.findRestaurantsAvgRating().stream()
        .map(stats -> new RestaurantRatingStatsDTO(stats.getRestaurantId(), stats.getAvgRating()))
        .toList();
  }
}
