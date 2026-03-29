package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.DeliveryFeeService;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.DeliveryFeeMapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.DeliveryFee;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.DeliveryFeeRepository;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.RegionService;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryFeeServiceImpl implements DeliveryFeeService {

  private final DeliveryFeeRepository deliveryFeeRepository;
  private final RegionService regionService;
  private final DeliveryFeeMapper deliveryFeeMapper;

  public DeliveryFeeDTO insertDeliveryFee(DeliveryFeeDTO dto) {
    DeliveryFee deliveryFee = deliveryFeeMapper.toDeliveryFee(dto);
    regionService.findRegionById(deliveryFee.getToRegionId());
    regionService.findRegionById(deliveryFee.getFromRegionId());

    return deliveryFeeMapper.toDeliveryDTO(deliveryFeeRepository.save(deliveryFee));
  }

  public DeliveryFeeDTO findDeliveryFeeBetweenTwoRegions(Long toRegionId, Long fromRegionId) {
    regionService.findRegionById(toRegionId);
    regionService.findRegionById(fromRegionId);

    return deliveryFeeRepository.findByToRegionIdAndFromRegionId(toRegionId, fromRegionId)
        .map(fee -> deliveryFeeMapper.toDeliveryDTO(fee))
        .orElseThrow(() -> new BusinessLogicException(
            String.format("No Delivery fee is found between %d and %d", toRegionId, fromRegionId)));
  }

  public DeliveryFeeDTO findById(Long id) {
    return deliveryFeeRepository.findById(id)
        .map(fee -> deliveryFeeMapper.toDeliveryDTO(fee))
        .orElseThrow(() -> new RecordNotFoundException(String.format("Delivery Fee of id %d is not found", id)));
  }

  public List<DeliveryFeeDTO> findAll() {
    return deliveryFeeRepository.findAll().stream()
        .map(fee -> deliveryFeeMapper.toDeliveryDTO(fee))
        .toList();
  }
}
