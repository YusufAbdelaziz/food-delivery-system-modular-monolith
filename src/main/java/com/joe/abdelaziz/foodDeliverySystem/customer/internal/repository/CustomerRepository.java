package com.joe.abdelaziz.foodDeliverySystem.customer.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.entity.*;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("SELECT c FROM Customer c WHERE c.role.type = :roleType")
  List<Customer> findAllByRoleType(RoleType roleType);

  @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
  Optional<Customer> findByPhoneNumber(String phoneNumber);
}








