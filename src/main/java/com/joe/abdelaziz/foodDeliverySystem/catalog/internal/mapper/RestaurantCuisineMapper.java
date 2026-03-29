package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Cuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Restaurant;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisine;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(uses = {RestaurantCuisineIdMapper.class, RestaurantMapper.class, CuisineMapper.class})
public abstract class RestaurantCuisineMapper {
  @Mapping(target = "restaurant", qualifiedByName = "toRestaurantWithoutCuisines")
  @Mapping(target = "cuisine", qualifiedByName = "toCuisineWithoutRestaurants")
  public abstract RestaurantCuisineDTO toRestaurantCuisineDTO(RestaurantCuisine restaurantCuisine);

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
      })
  public abstract RestaurantCuisine toRestaurantCuisine(RestaurantCuisineDTO dto);

  @AfterMapping
  public void linkRestaurantAndCuisine(
      @MappingTarget RestaurantCuisine entity, RestaurantCuisineDTO dto) {
    if (dto.getRestaurant() != null) {
      Restaurant restaurant = new Restaurant();
      restaurant.setId(dto.getRestaurant().getId());
      entity.setRestaurant(restaurant);
    }
    if (dto.getCuisine() != null) {
      Cuisine cuisine = new Cuisine();
      cuisine.setId(dto.getCuisine().getId());
      entity.setCuisine(cuisine);
    }
  }
}










