package com.joe.abdelaziz.foodDeliverySystem.location.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {}








