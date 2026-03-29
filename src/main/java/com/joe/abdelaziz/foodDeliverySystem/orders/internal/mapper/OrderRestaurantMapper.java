package com.joe.abdelaziz.foodDeliverySystem.orders.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.OrderRestaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {OrderItemMapper.class})
public abstract class OrderRestaurantMapper {

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
        @Mapping(target = "order", ignore = true)
      })
  public abstract OrderRestaurant toOrderRestaurant(OrderRestaurantDTO dto);

  @Mapping(target = "deliveryFeeId", ignore = true)
  public abstract OrderRestaurantDTO toOrderRestaurantDTO(OrderRestaurant orderRestaurant);
}







