package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.DeliveryFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public abstract class DeliveryFeeMapper {

  public abstract DeliveryFeeDTO toDeliveryDTO(DeliveryFee deliveryFee);

  @Mappings(
      value = {
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
      })
  public abstract DeliveryFee toDeliveryFee(DeliveryFeeDTO dto);

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
      })
  public abstract DeliveryFee toUpdatedDeliveryFee(
      DeliveryFeeDTO dto, @MappingTarget DeliveryFee existingDeliveryFee);
}










