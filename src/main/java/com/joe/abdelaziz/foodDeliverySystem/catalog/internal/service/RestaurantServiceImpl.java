package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantRatingStatsService;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service.CuisineService;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.RestaurantMapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Restaurant;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.RestaurantRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.AddressService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final AddressService addressService;
  private final CuisineService cuisineService;
  private final RestaurantMapper restaurantMapper;
  private final RestaurantRatingStatsService restaurantRatingStatsService;

  @Transactional
  public RestaurantDTO insertRestaurant(RestaurantDTO dto) {
    if (dto.getAddressId() == null) {
      throw new BusinessLogicException("Address id should be provided when creating a restaurant");
    }

    return restaurantMapper.toRestaurantDTO(
        restaurantRepository.save(restaurantMapper.toRestaurant(dto)));
  }

  private Restaurant getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a restaurant: id is null");
    }
    Restaurant restaurant = restaurantRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(
                String.format("Restaurant of record id %d is not found", id)));
    return restaurant;
  }

  public RestaurantDTO findDtoById(Long id) {
    Restaurant restaurant = getById(id);
    return restaurantMapper.toRestaurantDTO(restaurant);
  }

  public List<RestaurantDTO> getAll() {
    return restaurantRepository.findAll().stream()
        .map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant))
        .toList();
  }

  public List<RestaurantDTO> getRestaurantsByCuisineId(Long cuisineId) {
    // Check if the cuisine even exists
    cuisineService.getById(cuisineId);

    return restaurantRepository.findRestaurantsByCuisineId(cuisineId).stream()
        .map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant))
        .toList();
  }

  public void updateVisibility(Long restaurantId, boolean visible) {
    restaurantRepository.updateVisibility(restaurantId, visible);
  }

  public void deleteRestaurant(Long restaurantId) {
    restaurantRepository.deleteById(restaurantId);
  }

  public void removeRestaurantAddress(Long restaurantId, Long addressId) {
    getById(restaurantId);
    addressService.deleteRestaurantAddress(restaurantId, addressId);
  }

  public RestaurantDTO updateRestaurant(RestaurantDTO dto) {
    Restaurant existingRestaurant = getById(dto.getId());

    Restaurant updatedRestaurant = restaurantMapper.toUpdatedRestaurant(dto, existingRestaurant);
    return restaurantMapper.toRestaurantDTO(restaurantRepository.save(updatedRestaurant));
  }

  public List<RestaurantDTO> getByNameContaining(String restaurantName) {
    return restaurantRepository.findByNameContainingIgnoreCase(restaurantName).stream()
        .map(rest -> restaurantMapper.toRestaurantDTO(rest))
        .toList();
  }

  public void incrementSuccessfulOrdersCount(List<Long> restaurantIds) {
    List<Restaurant> restaurants = restaurantRepository.findAllById(restaurantIds);
    for (Restaurant restaurant : restaurants) {
      restaurant.setSuccessfulOrders(restaurant.getSuccessfulOrders() + 1);
    }
    restaurantRepository.saveAll(restaurants);
  }

  @Transactional
  public void updateAvgRatingsForAllRestaurants() {
    List<RestaurantRatingStatsDTO> orderRestaurantStats = restaurantRatingStatsService.findRestaurantsAvgRating();

    for (RestaurantRatingStatsDTO orderRestaurantStatsDTO : orderRestaurantStats) {
      restaurantRepository.updateAvgRating(
          orderRestaurantStatsDTO.getAvgRating(), orderRestaurantStatsDTO.getRestaurantId());
    }
  }
}
