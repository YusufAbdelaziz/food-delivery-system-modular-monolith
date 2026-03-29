package com.joe.abdelaziz.foodDeliverySystem.iam.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import java.util.SortedSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(imports = SortedSet.class)
public interface RoleMapper {

  RoleDTO toRoleDTO(Role role);

  @Mappings(
      value = {
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true)
      })
  Role toRole(RoleDTO roleDto);
}










