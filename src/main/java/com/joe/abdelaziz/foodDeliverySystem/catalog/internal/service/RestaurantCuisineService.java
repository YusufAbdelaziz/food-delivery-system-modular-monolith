package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.RestaurantRepository;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Cuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Restaurant;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisineId;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.RestaurantCuisineRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantCuisineService
    implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantCuisineService {

  private final RestaurantCuisineRepository restaurantCuisineRepository;
  private final CuisineService cuisineService;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantCuisineMapper restaurantCuisineMapper;
  private final CuisineMapper cuisineMapper;
  private final RestaurantCuisineIdMapper restaurantCuisineIdMapper;

  public RestaurantCuisineDTO getById(RestaurantCuisineIdDTO id) {
    return restaurantCuisineMapper.toRestaurantCuisineDTO(
        restaurantCuisineRepository
            .findById(restaurantCuisineIdMapper.toRestaurantCuisineId(id))
            .orElseThrow(
                () ->
                    new RecordNotFoundException(String.format("Cuisine id %d is not found", id))));
  }

  public List<RestaurantCuisineDTO> getAll() {
    return restaurantCuisineRepository.findAll().stream()
        .map(rs -> restaurantCuisineMapper.toRestaurantCuisineDTO(rs))
        .toList();
  }

  public RestaurantCuisineDTO insert(RestaurantCuisineDTO dto) {

    RestaurantCuisine restaurantCuisine = restaurantCuisineMapper.toRestaurantCuisine(dto);
    Restaurant restaurant = restaurantRepository.findById(restaurantCuisine.getRestaurant().getId())
        .orElseThrow(
            () -> new RecordNotFoundException(
                String.format(
                    "Restaurant of record id %d is not found",
                    restaurantCuisine.getRestaurant().getId())));
    Cuisine cuisine =
        cuisineMapper.toCuisine(cuisineService.getById(restaurantCuisine.getCuisine().getId()));
    RestaurantCuisineId id = new RestaurantCuisineId(restaurant.getId(), cuisine.getId());

    restaurantCuisine.setCuisine(cuisine);
    restaurantCuisine.setRestaurant(restaurant);
    restaurantCuisine.setId(id);

    return restaurantCuisineMapper.toRestaurantCuisineDTO(
        restaurantCuisineRepository.save(restaurantCuisine));
  }

  public void deleteRestaurantCuisine(RestaurantCuisineIdDTO dto) {
    RestaurantCuisineId id = restaurantCuisineIdMapper.toRestaurantCuisineId(dto);
    restaurantCuisineRepository.deleteById(id);
  }
}













