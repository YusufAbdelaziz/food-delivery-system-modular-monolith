package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Spec;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.SpecOption;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.SpecOptionRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SpecOptionService
    implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.SpecOptionService {

  private final SpecOptionRepository specOptionRepository;
  private final SpecService specService;
  private final SpecOptionMapper specOptionMapper;

  @Transactional
  public SpecOptionDTO insertSpecOption(SpecOptionDTO dto, Long specId) {
    SpecOption specOption = specOptionMapper.toSpecOption(dto);
    Spec existingSpec = specService.getById(specId);

    SpecOption newOption = specOptionRepository.save(specOption);
    existingSpec.addOption(newOption);

    return specOptionMapper.toSpecOptionDTO(newOption);
  }

  public SpecOptionDTO updateSpecOption(SpecOptionDTO updatedOption) {
    SpecOption existingOption = getEntityById(updatedOption.getId());
    SpecOption updatedEntity = specOptionMapper.toUpdatedSpecOption(updatedOption, existingOption);
    return specOptionMapper.toSpecOptionDTO(specOptionRepository.save(updatedEntity));
  }

  public void deleteItem(Long id) {
    SpecOption existingSpecOption = getEntityById(id);
    specOptionRepository.delete(existingSpecOption);
  }

  public SpecOptionDTO getById(Long id) {
    return specOptionMapper.toSpecOptionDTO(getEntityById(id));
  }

  private SpecOption getEntityById(Long id) {
    if (id == null) {
      throw new BusinessLogicException("Spec option id is null");
    }
    return specOptionRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Spec option id %d is not found", id)));
  }
}













