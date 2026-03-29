package com.joe.abdelaziz.foodDeliverySystem.orders.internal.mapper;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {OrderRestaurantMapper.class})
public abstract class OrderMapper {

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedBy", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public abstract Order toOrder(OrderDTO dto);

  @Mapping(target = "promotionCode", ignore = true)
  public abstract OrderDTO toOrderDTO(Order order);
}







