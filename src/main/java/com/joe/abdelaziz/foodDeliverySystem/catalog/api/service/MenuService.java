package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import java.util.List;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.MenuDTO;

public interface MenuService {
  MenuDTO insertMenu(MenuDTO dto);

  MenuDTO getByRestaurantId(Long id);

  MenuDTO getDTOById(long id);

  List<MenuDTO> getAll();

  void deleteMenu(long id);
}
