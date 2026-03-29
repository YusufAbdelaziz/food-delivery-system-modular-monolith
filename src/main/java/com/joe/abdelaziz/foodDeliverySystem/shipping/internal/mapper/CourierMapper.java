package com.joe.abdelaziz.foodDeliverySystem.shipping.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.CourierDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.CourierOrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.internal.entity.Courier;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CourierMapper {

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "password", ignore = true),
      @Mapping(target = "active", ignore = true),
      @Mapping(target = "avgRating", ignore = true),
      @Mapping(target = "earnings", ignore = true),
      @Mapping(target = "role", ignore = true),
      @Mapping(target = "successfulOrders", ignore = true),
      @Mapping(target = "authorities", ignore = true)
  })
  public abstract Courier toCourier(CourierOrderDTO courier);

  public abstract CourierOrderDTO toCourierOrderDTO(Courier courier);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "authorities", ignore = true),
      @Mapping(target = "role", ignore = true)
  })
  public abstract Courier toUpdatedCourier(
      CourierDTO dto, @MappingTarget Courier existingPromotion);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "role", expression = "java(mapRole(dto.getRoleId()))"),
      @Mapping(target = "authorities", ignore = true)
  })
  public abstract Courier toCourier(CourierDTO dto);

  @Mapping(target = "orderIds", ignore = true)
  @Mapping(target = "roleId", expression = "java(courier.getRole() != null ? courier.getRole().getId() : null)")
  public abstract CourierDTO toCourierDTO(Courier courier);

  protected Role mapRole(Long roleId) {
    if (roleId == null) {
      return null;
    }
    Role role = new Role();
    role.setId(roleId);
    return role;
  }
}
