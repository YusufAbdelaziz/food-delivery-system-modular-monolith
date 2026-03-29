package com.joe.abdelaziz.foodDeliverySystem.shipping.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.shipping.internal.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDeliveryFeeRepository
    extends JpaRepository<RestaurantDeliveryFee, Long> {}








