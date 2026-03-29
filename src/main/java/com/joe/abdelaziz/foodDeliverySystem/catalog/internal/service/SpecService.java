package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Item;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Spec;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.SpecRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecService implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.SpecService {

  private final SpecRepository specRepository;
  private final ItemService itemService;
  private final SpecMapper specMapper;
  private final SpecOptionMapper specOptionMapper;

  @Transactional
  public SpecDTO insertSpec(SpecDTO dto, Long itemId) {
    Spec spec = specMapper.toSpec(dto);
    Item item = itemService.getById(itemId);

    Spec savedSpec = specRepository.save(spec);
    item.addSpec(savedSpec);
    return specMapper.toSpecDTO(savedSpec);
  }

  @Transactional
  public SpecDTO insertSpecOptions(Long specId, List<SpecOptionDTO> options) {

    Spec existingSpec = getById(specId);
    existingSpec.addOptions(
        options.stream().map(option -> specOptionMapper.toSpecOption(option)).toList());
    Spec savedSpec = specRepository.saveAndFlush(existingSpec);
    return specMapper.toSpecDTO(savedSpec);
  }

  public SpecDTO updateSpec(SpecDTO dto) {

    // Fetch the existing section from the table
    Spec existingSpec = getById(dto.getId());

    Spec updatedSpec = specMapper.toUpdatedSpec(dto, existingSpec);
    // Save the updated section
    return specMapper.toSpecDTO(specRepository.save(updatedSpec));
  }

  public void deleteSpec(Long id) {
    Spec existingSpec = getById(id);
    specRepository.delete(existingSpec);
  }

  public Spec getById(Long id) {
    return specRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Spec id %d is not found", id)));
  }

  public SpecDTO getDTOById(Long id) {
    return specMapper.toSpecDTO(getById(id));
  }
}













