package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.RestaurantRepository;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Menu;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.MenuRepository;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Restaurant;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.MenuService {

  private final MenuRepository menuRepository;
  private final RestaurantRepository restaurantRepository;
  private final MenuMapper menuMapper;

  @Transactional
  public MenuDTO insertMenu(MenuDTO dto) {
    Menu menu = menuMapper.toMenu(dto);
    if (dto.getRestaurant() == null) {
      throw new BusinessLogicException("Restaurant entity should be provided (restaurant_id)");
    }
    Restaurant restaurant = restaurantRepository.findById(dto.getRestaurant().getId())
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Restaurant of record id %d is not found", dto.getRestaurant().getId())));
    menu.setRestaurant(restaurant);
    Menu insertedMenu = menuRepository.save(menu);
    restaurant.setMenu(insertedMenu);

    return menuMapper.toMenuDTO(insertedMenu);
  }

  public MenuDTO getByRestaurantId(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a Menu: id is null");
    }
    Menu menu = menuRepository.findByRestaurant(id);
    return menuMapper.toMenuDTO(menu);
  }

  public Menu getById(long id) {
    return menuRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Menu id %d is not found", id)));
  }

  public MenuDTO getDTOById(long id) {
    return menuMapper.toMenuDTO(getById(id));
  }

  public List<MenuDTO> getAll() {
    return menuRepository.findAll().stream().map(menu -> menuMapper.toMenuDTO(menu)).toList();
  }

  @Transactional
  public void deleteMenu(long id) {
    Menu menu = getById(id);

    if (menu.getRestaurant() != null) {
      Restaurant restaurant = menu.getRestaurant();
      restaurant.setMenu(null);
      restaurantRepository.save(restaurant);
    }

    menuRepository.delete(menu);
  }
}













