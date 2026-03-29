package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.service;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderPromotionService;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.PromotionDTO;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.service.PromotionService;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.service.UserPromotionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderPromotionServiceImpl implements OrderPromotionService {

  private final PromotionService promotionService;
  private final UserPromotionService userPromotionService;

  @Override
  public void validatePromotionForOrder(Long promotionId) {
    PromotionDTO promotion = promotionService.getDtoById(promotionId);
    promotionService.validatePromotion(promotion);
  }

  @Override
  public void registerPromotionUsage(Long promotionId, Long customerId) {
    promotionService.incrementUsageCount(promotionId);
    userPromotionService.assignPromotionToUser(promotionId, customerId);
  }

  @Override
  public OrderDTO applyPromotionCode(String promotionCode, OrderDTO order) {
    return promotionService.applyPromotionCode(promotionCode, order);
  }
}
