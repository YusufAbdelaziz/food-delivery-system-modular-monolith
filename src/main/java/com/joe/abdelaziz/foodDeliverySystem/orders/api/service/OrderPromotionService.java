package com.joe.abdelaziz.foodDeliverySystem.orders.api.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

public interface OrderPromotionService {
  void validatePromotionForOrder(Long promotionId);

  void registerPromotionUsage(Long promotionId, Long customerId);

  OrderDTO applyPromotionCode(String promotionCode, OrderDTO order);
}
