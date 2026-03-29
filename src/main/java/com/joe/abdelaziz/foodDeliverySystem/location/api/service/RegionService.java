package com.joe.abdelaziz.foodDeliverySystem.location.api.service;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.RegionDto;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Region;
import java.util.List;

public interface RegionService {
  Region findRegionById(Long id);

  RegionDto findRegionDtoById(Long id);

  RegionDto insertRegion(RegionDto dto);

  List<RegionDto> findAll();
}
