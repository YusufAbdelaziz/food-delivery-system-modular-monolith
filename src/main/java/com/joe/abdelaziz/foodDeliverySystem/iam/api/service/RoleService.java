package com.joe.abdelaziz.foodDeliverySystem.iam.api.service;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
  Optional<Role> findRoleById(Long id);

  Role insertRole(Role role);

  Role findRoleByType(RoleType type);

  List<Role> getAllRoles();
}

