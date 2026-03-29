package com.joe.abdelaziz.foodDeliverySystem.user.api.service;

import com.joe.abdelaziz.foodDeliverySystem.user.api.dto.AdminDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
  AdminDTO insertAdmin(AdminDTO user);

  AdminDTO findAdminById(Long id);

  List<AdminDTO> findAll();

  Optional<UserDetails> findByPhoneNumber(String phoneNumber);
}
