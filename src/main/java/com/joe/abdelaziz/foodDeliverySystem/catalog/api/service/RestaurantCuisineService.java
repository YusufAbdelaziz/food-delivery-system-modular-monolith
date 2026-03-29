package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantCuisineDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantCuisineIdDTO;

public interface RestaurantCuisineService {
  RestaurantCuisineDTO getById(RestaurantCuisineIdDTO id);

  List<RestaurantCuisineDTO> getAll();

  RestaurantCuisineDTO insert(RestaurantCuisineDTO dto);

  void deleteRestaurantCuisine(RestaurantCuisineIdDTO dto);
}
