package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.SpecOptionDTO;

public interface SpecOptionService {
  SpecOptionDTO insertSpecOption(SpecOptionDTO dto, Long specId);

  SpecOptionDTO updateSpecOption(SpecOptionDTO updatedOption);

  void deleteItem(Long id);

  SpecOptionDTO getById(Long id);
}
