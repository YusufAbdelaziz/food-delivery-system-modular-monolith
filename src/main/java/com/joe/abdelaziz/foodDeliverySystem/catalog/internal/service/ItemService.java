package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Item;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.ItemRepository;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Section;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.ItemService {

  private final ItemRepository itemRepository;
  private final SectionService sectionService;
  private final ItemMapper itemMapper;
  private final SpecMapper specMapper;

  @Transactional
  public ItemDTO insertItem(ItemDTO dto, Long sectionId) {

    Section section = sectionService.getById(sectionId);
    Item item = itemMapper.toItem(dto);

    Item savedItem = itemRepository.save(item);
    section.addItem(savedItem);
    return itemMapper.toItemDTO(savedItem);
  }

  @Transactional
  public ItemDTO insertSpecs(Long itemId, List<SpecDTO> specs) {

    Item existingItem = getById(itemId);
    existingItem.addSpecs(specs.stream().map(spec -> specMapper.toSpec(spec)).toList());
    Item savedItem = itemRepository.saveAndFlush(existingItem);
    return itemMapper.toItemDTO(savedItem);
  }

  public ItemDTO updateItem(ItemDTO dto) {

    Item existingItem = getById(dto.getId());

    Item updatedItem = itemMapper.toUpdatedItem(dto, existingItem);
    // Save the updated section
    return itemMapper.toItemDTO(itemRepository.save(updatedItem));
  }

  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }

  public Item getById(Long id) {

    return itemRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Item id %d is not found", id)));
  }

  public ItemDTO getDTOById(Long id) {
    return itemMapper.toItemDTO(getById(id));
  }
}













