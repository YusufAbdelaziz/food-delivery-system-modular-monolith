package com.joe.abdelaziz.foodDeliverySystem.user.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import com.joe.abdelaziz.foodDeliverySystem.user.api.dto.AdminDTO;
import com.joe.abdelaziz.foodDeliverySystem.user.internal.entity.Admin;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AdminMapper {

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedBy", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "role", expression = "java(mapRole(dto.getRoleId()))")
  @Mapping(target = "authorities", ignore = true)
  public abstract Admin toAdmin(AdminDTO dto);

  @Mapping(target = "roleId", expression = "java(admin.getRole() != null ? admin.getRole().getId() : null)")
  public abstract AdminDTO toAdminDTO(Admin admin);

  protected Role mapRole(Long roleId) {
    if (roleId == null) {
      return null;
    }
    Role role = new Role();
    role.setId(roleId);
    return role;
  }
}
