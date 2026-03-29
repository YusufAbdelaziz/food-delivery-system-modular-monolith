package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.*;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryFeeRepository extends JpaRepository<DeliveryFee, Long> {

  Optional<DeliveryFee> findByToRegionIdAndFromRegionId(Long toRegionId, Long fromRegionId);
}








