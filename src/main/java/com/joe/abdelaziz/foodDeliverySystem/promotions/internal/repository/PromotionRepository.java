package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.*;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
  Optional<Promotion> findByCodeAndActiveTrue(String code);
}








