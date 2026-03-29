package com.joe.abdelaziz.foodDeliverySystem.user.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.user.internal.entity.*;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

  @Query("SELECT a FROM Admin a WHERE a.role.type = :roleType")
  List<Admin> findAllByRoleType(RoleType roleType);

  @Query("SELECT a FROM Admin a WHERE a.phoneNumber = :phoneNumber")
  Optional<Admin> findByPhoneNumber(String phoneNumber);
}








