package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantRatingStatsDTO;

public interface RestaurantRatingStatsService {
  List<RestaurantRatingStatsDTO> findRestaurantsAvgRating();
}
