package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantDTO;

public interface RestaurantService {
  RestaurantDTO insertRestaurant(RestaurantDTO dto);

  RestaurantDTO findDtoById(Long id);

  List<RestaurantDTO> getAll();

  List<RestaurantDTO> getRestaurantsByCuisineId(Long cuisineId);

  void updateVisibility(Long restaurantId, boolean visible);

  void deleteRestaurant(Long restaurantId);

  void removeRestaurantAddress(Long restaurantId, Long addressId);

  RestaurantDTO updateRestaurant(RestaurantDTO dto);

  List<RestaurantDTO> getByNameContaining(String restaurantName);

  void incrementSuccessfulOrdersCount(List<Long> restaurantIds);

  void updateAvgRatingsForAllRestaurants();
}
