package com.joe.abdelaziz.foodDeliverySystem.iam.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByType(RoleType type);
}








