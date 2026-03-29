package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Cuisine;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.CuisineRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuisineService implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.CuisineService {

  private final CuisineRepository cuisineRepository;
  private final CuisineMapper cuisineMapper;

  public CuisineDTO getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a cuisine: id is null");
    }
    return cuisineMapper.toCuisineDTOWithoutRestaurants(
        cuisineRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new RecordNotFoundException(String.format("Cuisine id %d is not found", id))));
  }

  public CuisineDTO insert(CuisineDTO cuisine) {
    Cuisine newCuisine = cuisineRepository.save(cuisineMapper.toCuisine(cuisine));
    return cuisineMapper.toCuisineDTOWithoutRestaurants(newCuisine);
  }

  public List<CuisineDTO> getAll() {
    return cuisineRepository.findAll().stream()
        .map(cuisine -> cuisineMapper.toCuisineDTOWithoutRestaurants(cuisine))
        .toList();
  }
}













