package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.SpecDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.SpecOptionDTO;

public interface SpecService {
  SpecDTO insertSpec(SpecDTO dto, Long itemId);

  SpecDTO insertSpecOptions(Long specId, List<SpecOptionDTO> options);

  SpecDTO updateSpec(SpecDTO dto);

  void deleteSpec(Long id);

  SpecDTO getDTOById(Long id);
}
