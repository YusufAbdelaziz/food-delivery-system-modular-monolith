package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.service.UserPromotionService;

import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.Promotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.UserPromotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.repository.UserPromotionRepository;
import com.joe.abdelaziz.foodDeliverySystem.promotions.userPromotion.UserPromotionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPromotionServiceImpl implements UserPromotionService {

  private final UserPromotionRepository userPromotionRepository;
  private final UserPromotionMapper userPromotionMapper;

  public void assignPromotionToUser(Long promotionId, Long customerId) {
    UserPromotion newUserPromotion = new UserPromotion();
    Promotion promotion = new Promotion();
    promotion.setId(promotionId);
    newUserPromotion.setCustomerId(customerId);
    newUserPromotion.setPromotion(promotion);
    userPromotionRepository.save(newUserPromotion);
  }

  public List<UserPromotionDTO> getAll() {
    return userPromotionRepository.findAll().stream()
        .map(up -> userPromotionMapper.toUserPromotionDto(up))
        .toList();
  }
}










