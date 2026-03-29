package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPromotionRepository extends JpaRepository<UserPromotion, Long> {}








