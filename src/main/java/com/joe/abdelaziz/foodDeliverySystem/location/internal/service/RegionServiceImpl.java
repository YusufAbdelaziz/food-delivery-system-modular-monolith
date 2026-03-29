package com.joe.abdelaziz.foodDeliverySystem.location.internal.service;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.RegionDto;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.RegionService;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.mapper.RegionMapper;

import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Region;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

  private final RegionRepository regionRepository;
  private final RegionMapper regionMapper;

  public Region findRegionById(Long id) {
    Optional<Region> opRegion = regionRepository.findById(id);
    if (opRegion.isEmpty()) {
      throw new EntityNotFoundException(String.format("Region id %d is not found", id));
    }
    return opRegion.get();
  }

  public RegionDto findRegionDtoById(Long id) {
    return regionMapper.toRegionDto(findRegionById(id));
  }

  public RegionDto insertRegion(RegionDto dto) {
    Region region = regionMapper.toRegion(dto);
    Region insertedRegion = regionRepository.save(region);
    return regionMapper.toRegionDto(insertedRegion);
  }

  public List<RegionDto> findAll() {
    return regionRepository.findAll().stream()
        .map(region -> regionMapper.toRegionDto(region))
        .toList();
  }
}







