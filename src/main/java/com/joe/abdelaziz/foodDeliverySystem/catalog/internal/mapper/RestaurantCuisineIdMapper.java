package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisineId;
import org.mapstruct.Mapper;

@Mapper
public abstract class RestaurantCuisineIdMapper {

  public abstract RestaurantCuisineIdDTO toRestaurantCuisineIdDTO(
      RestaurantCuisineId restaurantCuisineId);

  public abstract RestaurantCuisineId toRestaurantCuisineId(RestaurantCuisineIdDTO dto);
}










