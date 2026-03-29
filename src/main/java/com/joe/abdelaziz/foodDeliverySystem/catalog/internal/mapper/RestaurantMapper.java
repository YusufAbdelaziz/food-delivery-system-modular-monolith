package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Cuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Restaurant;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.RestaurantCuisineId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    uses = {MenuMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RestaurantMapper {

  @Autowired private CuisineMapper cuisineMapper;

  @Autowired private RestaurantCuisineIdMapper rsIdMapper;

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
        @Mapping(target = "cuisines", ignore = true),
      })
  public abstract Restaurant toRestaurant(RestaurantDTO dto);

  @Mapping(target = "cuisines", ignore = true)
  public abstract RestaurantDTO toRestaurantDTO(Restaurant restaurant);

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
        @Mapping(target = "cuisines", ignore = true),
      })
  public abstract Restaurant toUpdatedRestaurant(
      RestaurantDTO dto, @MappingTarget Restaurant existingAddress);

  @Named("toRestaurantWithoutCuisines")
  @Mapping(target = "cuisines", ignore = true)
  public abstract RestaurantDTO toRestaurantWithoutCuisines(Restaurant entity);

  @AfterMapping
  public void mapCuisines(Restaurant entity, @MappingTarget RestaurantDTO dto) {
    Set<RestaurantCuisineDTO> rsSet = new HashSet<>();
    if (entity.getCuisines() != null) {
      rsSet =
          entity.getCuisines().stream()
              .map(
                  rs -> {
                    RestaurantCuisineDTO rsDTO = new RestaurantCuisineDTO();
                    rsDTO.setCuisine(cuisineMapper.toCuisineDTOWithoutRestaurants(rs.getCuisine()));
                    rsDTO.setId(rsIdMapper.toRestaurantCuisineIdDTO(rs.getId()));
                    return rsDTO;
                  })
              .collect(Collectors.toSet());
    }

    dto.setCuisines(rsSet);
  }

  @AfterMapping
  public void linkCuisines(@MappingTarget Restaurant entity, RestaurantDTO dto) {
    if (dto.getCuisines() != null) {
      entity.setCuisines(
          dto.getCuisines().stream()
              .map(
                  rc -> {
                    RestaurantCuisine restaurantCuisine = new RestaurantCuisine();
                    RestaurantCuisineId id = new RestaurantCuisineId();
                    id.setRestaurantId(entity.getId());
                    id.setCuisineId(rc.getCuisine().getId());
                    restaurantCuisine.setId(id);
                    restaurantCuisine.setRestaurant(entity);
                    Cuisine cuisine = new Cuisine();
                    cuisine.setId(rc.getCuisine().getId());
                    restaurantCuisine.setCuisine(cuisine);
                    return restaurantCuisine;
                  })
              .collect(Collectors.toSet()));
    }
  }
}










