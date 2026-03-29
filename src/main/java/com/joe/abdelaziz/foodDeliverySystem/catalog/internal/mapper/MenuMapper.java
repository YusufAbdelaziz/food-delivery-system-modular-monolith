package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {SectionMapper.class})
public abstract class MenuMapper {

  @Mapping(target = "restaurant", ignore = true)
  public abstract MenuDTO toMenuDTO(Menu menu);

  @Mappings(
      value = {
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "lastModifiedBy", ignore = true),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "lastModifiedDate", ignore = true),
        @Mapping(target = "restaurant", ignore = true),
      })
  public abstract Menu toMenu(MenuDTO dto);
}










