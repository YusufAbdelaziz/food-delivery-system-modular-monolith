package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.ItemDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.SpecDTO;

public interface ItemService {
  ItemDTO insertItem(ItemDTO dto, Long sectionId);

  ItemDTO insertSpecs(Long itemId, List<SpecDTO> specs);

  ItemDTO updateItem(ItemDTO dto);

  void deleteItem(Long id);

  ItemDTO getDTOById(Long id);
}
