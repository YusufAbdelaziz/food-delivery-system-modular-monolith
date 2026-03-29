package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.CuisineDTO;

public interface CuisineService {
  CuisineDTO getById(Long id);

  CuisineDTO insert(CuisineDTO cuisine);

  List<CuisineDTO> getAll();
}
