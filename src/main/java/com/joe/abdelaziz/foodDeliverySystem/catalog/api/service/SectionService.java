package com.joe.abdelaziz.foodDeliverySystem.catalog.api.service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.SectionDTO;

public interface SectionService {
  SectionDTO insertSection(SectionDTO sectionDto, long menuId);

  SectionDTO getDTOById(Long id);

  SectionDTO updateSection(SectionDTO dto);

  void deleteSection(Long id);
}
