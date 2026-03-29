package com.joe.abdelaziz.foodDeliverySystem.orders.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderSpecDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.OrderSpec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {OrderOptionMapper.class})
public abstract class OrderSpecMapper {

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true)
      })
  public abstract OrderSpec toOrderSpec(OrderSpecDTO dto);

  public abstract OrderSpecDTO toOrderSpecDTO(OrderSpec orderRestaurant);
}







