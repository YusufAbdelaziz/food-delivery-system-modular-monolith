package com.joe.abdelaziz.foodDeliverySystem.shipping.internal.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.DeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.RestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.internal.entity.RestaurantDeliveryFee;

@Mapper
public abstract class RestaurantDeliveryFeeMapper {

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract RestaurantDeliveryFee toRestaurantDeliveryFee(RestaurantDeliveryFeeDTO dto);

  @Mapping(target = "discountedPrice", ignore = true)
  public abstract RestaurantDeliveryFeeDTO toRestaurantDeliveryFeeDTO(
      RestaurantDeliveryFee orderDeliveryFee);

  @Mappings({
      @Mapping(target = "currentPrice", source = "price"),
      // Id should be ignored as we're creating a new OrderDeliveryFee
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "toRegionId", source = "toRegionId"),
      @Mapping(target = "fromRegionId", source = "fromRegionId"),
      @Mapping(target = "orderRestaurantId", ignore = true),
      @Mapping(target = "discountedPrice", ignore = true)
  })
  public abstract RestaurantDeliveryFee toRestaurantDeliveryFee(DeliveryFeeDTO deliveryFee);

  // Notice that the delivery fee changes when a promotion is applied.
  @AfterMapping
  protected void afterMappingToDeliveryFee(
      @MappingTarget RestaurantDeliveryFee deliveryFee, RestaurantDeliveryFeeDTO dto) {
    if (dto.getDiscountedPrice() != null) {
      deliveryFee.setCurrentPrice(dto.getDiscountedPrice());
    } else {
      deliveryFee.setCurrentPrice(dto.getCurrentPrice());
    }
  }
}
