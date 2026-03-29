package com.joe.abdelaziz.foodDeliverySystem.promotions.userPromotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.Promotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.UserPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class UserPromotionMapper {

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
        @Mapping(target = "promotion", expression = "java(mapPromotion(dto.getPromotionId()))")
      })
  public abstract UserPromotion toUserPromotion(UserPromotionDTO dto);

  @Mapping(
      target = "promotionId",
      expression = "java(promotion.getPromotion() != null ? promotion.getPromotion().getId() : null)")
  public abstract UserPromotionDTO toUserPromotionDto(UserPromotion promotion);

  protected Promotion mapPromotion(Long promotionId) {
    if (promotionId == null) {
      return null;
    }
    Promotion promotion = new Promotion();
    promotion.setId(promotionId);
    return promotion;
  }
}








