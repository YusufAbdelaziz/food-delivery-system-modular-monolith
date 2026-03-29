package com.joe.abdelaziz.foodDeliverySystem.location.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.*;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Transactional
  @Modifying
  @Query(
      value =
          "UPDATE address SET active = false WHERE user_id = :userId AND address_id !="
              + " :activeAddressId",
      nativeQuery = true)
  int updateAddressActivityByUserId(Long activeAddressId, Long userId);

  List<Address> findByCustomerId(Long customerId);

  @Query(
      value =
          "SELECT COUNT(*) FROM restaurant WHERE restaurant_id = :restaurantId"
              + " AND address_id = :addressId",
      nativeQuery = true)
  long countRestaurantsByRestaurantIdAndAddressId(Long restaurantId, Long addressId);

  @Transactional
  @Modifying
  @Query(
      value =
          "UPDATE restaurant SET address_id = NULL WHERE restaurant_id = :restaurantId"
              + " AND address_id = :addressId",
      nativeQuery = true)
  int clearRestaurantAddress(Long restaurantId, Long addressId);
}








