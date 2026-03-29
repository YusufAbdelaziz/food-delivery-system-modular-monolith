package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.promotion;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.lang.NonNull;

import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.PromotionDTO;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.Promotion;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PromotionMapper {

    @NonNull
    @Mappings(value = {
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "userPromotions", ignore = true)
    })
    public abstract Promotion toPromotion(PromotionDTO dto);

    @NonNull
    public abstract PromotionDTO toPromotionDto(Promotion promotion);

    public abstract List<PromotionDTO> toPromotionDtos(List<Promotion> promotion);

    @NonNull
    @Mappings(value = {
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "userPromotions", ignore = true)
    })
    public abstract Promotion toUpdatePromotion(
            PromotionDTO dto, @MappingTarget Promotion existingPromotion);
}
