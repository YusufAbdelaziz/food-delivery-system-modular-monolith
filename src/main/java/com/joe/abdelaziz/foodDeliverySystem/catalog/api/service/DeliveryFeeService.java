package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.DeliveryFeeDTO;

public interface DeliveryFeeService {
  DeliveryFeeDTO insertDeliveryFee(DeliveryFeeDTO dto);

  DeliveryFeeDTO findDeliveryFeeBetweenTwoRegions(Long toRegionId, Long fromRegionId);

  DeliveryFeeDTO findById(Long id);

  List<DeliveryFeeDTO> findAll();
}
