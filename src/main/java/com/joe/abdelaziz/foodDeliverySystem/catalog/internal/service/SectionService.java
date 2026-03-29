package com.joe.abdelaziz.foodDeliverySystem.catalog.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.mapper.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.enums.*;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.*;

import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Menu;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.entity.Section;
import com.joe.abdelaziz.foodDeliverySystem.catalog.internal.repository.SectionRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SectionService implements com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.SectionService {

  private final SectionRepository sectionRepository;
  private final MenuService menuService;
  private final SectionMapper sectionMapper;

  @Transactional
  public SectionDTO insertSection(SectionDTO sectionDto, long menuId) {

    Section section = sectionMapper.toSection(sectionDto);
    Menu menu = menuService.getById(menuId);
    Section insertedSection = sectionRepository.save(section);
    menu.addSection(insertedSection);

    return sectionMapper.toSectionDTO(insertedSection);
  }

  public Section getById(long id) {
    return sectionRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Section id %d is not found", id)));
  }

  public SectionDTO getDTOById(Long id) {
    return sectionMapper.toSectionDTO(getById(id));
  }

  @Transactional
  public SectionDTO updateSection(SectionDTO dto) {

    // Fetch the existing section from the table
    Section existingSection = getById(dto.getId());

    Section updatedSection = sectionMapper.toUpdatedSection(dto, existingSection);
    // Save the updated section
    return sectionMapper.toSectionDTO(sectionRepository.save(updatedSection));
  }

  public void deleteSection(Long id) {
    Section existingSection = getById(id);

    sectionRepository.delete(existingSection);
  }
}













