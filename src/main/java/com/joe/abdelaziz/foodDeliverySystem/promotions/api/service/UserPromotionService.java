package com.joe.abdelaziz.foodDeliverySystem.promotions.api.service;

import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.UserPromotionDTO;
import java.util.List;

public interface UserPromotionService {
  void assignPromotionToUser(Long promotionId, Long customerId);

  List<UserPromotionDTO> getAll();
}
