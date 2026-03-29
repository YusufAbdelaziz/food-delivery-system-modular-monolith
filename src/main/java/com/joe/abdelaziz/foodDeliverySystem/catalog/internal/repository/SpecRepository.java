package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {}








