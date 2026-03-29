package com.joe.abdelaziz.foodDeliverySystem.location.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.RegionDto;

import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper()
public interface RegionMapper {

  RegionDto toRegionDto(Region region);

  @Mappings(
      value = {
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true)
      })
  Region toRegion(RegionDto dto);
}







