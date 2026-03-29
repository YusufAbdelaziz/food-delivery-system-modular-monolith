package com.joe.abdelaziz.foodDeliverySystem.user.internal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.service.RoleService;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import com.joe.abdelaziz.foodDeliverySystem.user.api.dto.AdminDTO;
import com.joe.abdelaziz.foodDeliverySystem.user.api.service.AdminService;
import com.joe.abdelaziz.foodDeliverySystem.user.internal.entity.Admin;
import com.joe.abdelaziz.foodDeliverySystem.user.internal.mapper.AdminMapper;
import com.joe.abdelaziz.foodDeliverySystem.user.internal.repository.AdminRepository;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final AdminMapper adminMapper;
  private final RoleService roleService;

  @Transactional
  public AdminDTO insertAdmin(AdminDTO user) {
    Admin admin = adminMapper.toAdmin(user);
    Role role = roleService.findRoleByType(RoleType.ADMIN);
    admin.setRole(role);

    Admin insertedUser = adminRepository.save(admin);
    return adminMapper.toAdminDTO(insertedUser);
  }

  public AdminDTO findAdminById(Long id) {
    Admin user = adminRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Admin of record id %d is not found", id)));
    return adminMapper.toAdminDTO(user);
  }

  public List<AdminDTO> findAll() {
    return adminRepository.findAllByRoleType(RoleType.ADMIN).stream()
        .map(adminMapper::toAdminDTO)
        .toList();
  }

  public Optional<UserDetails> findByPhoneNumber(String phoneNumber) {
    return adminRepository.findByPhoneNumber(phoneNumber).map(admin -> (UserDetails) admin);
  }
}








