package com.joe.abdelaziz.foodDeliverySystem.orders.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.*;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantStatsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRestaurantRepository extends JpaRepository<OrderRestaurant, Long> {

  @Query(nativeQuery = true, name = "OrderRestaurant.findRestaurantsAvgRating")
  List<OrderRestaurantStatsDTO> findRestaurantsAvgRating();
}








