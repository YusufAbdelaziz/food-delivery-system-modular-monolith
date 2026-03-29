package com.joe.abdelaziz.foodDeliverySystem.orders.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderItemDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {OrderSpecMapper.class})
public abstract class OrderItemMapper {

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true)
      })
  public abstract OrderItem toOrderItem(OrderItemDTO dto);

  public abstract OrderItemDTO toOrderItemDTO(OrderItem orderItem);
}







