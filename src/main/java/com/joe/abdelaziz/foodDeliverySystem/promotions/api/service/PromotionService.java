package com.joe.abdelaziz.foodDeliverySystem.promotions.api.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.PromotionDTO;
import java.util.List;

public interface PromotionService {
  PromotionDTO insertPromotion(PromotionDTO promotionDTO);

  OrderDTO applyPromotionCode(String promotionCode, OrderDTO order);

  void incrementUsageCount(Long promotionId);

  void validatePromotion(PromotionDTO promotion);

  PromotionDTO getDtoById(long id);

  List<PromotionDTO> getAll();

  PromotionDTO deactivatePromotion(Long id);

  PromotionDTO updatePromotion(PromotionDTO dto);

  void deleteById(Long id);
}
