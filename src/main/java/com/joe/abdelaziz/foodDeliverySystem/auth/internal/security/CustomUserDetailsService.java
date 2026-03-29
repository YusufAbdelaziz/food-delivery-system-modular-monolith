package com.joe.abdelaziz.foodDeliverySystem.auth.internal.security;

import com.joe.abdelaziz.foodDeliverySystem.customer.api.service.CustomerService;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.service.CourierService;
import com.joe.abdelaziz.foodDeliverySystem.user.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final CourierService courierService;
  private final AdminService adminService;
  private final CustomerService customerService;

  @Override
  public UserDetails loadUserByUsername(String roleAndPhoneNumber)
      throws UsernameNotFoundException {
    // Assume the username is in the format "role:phoneNumber"
    String[] parts = roleAndPhoneNumber.split(":");
    if (parts.length != 2) {
      throw new UsernameNotFoundException("Invalid role and phone number format");
    }

    String role = parts[0].toUpperCase();
    String phoneNumber = parts[1];

    switch (role.toUpperCase()) {
      case "ADMIN":
        return adminService
            .findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
      case "USER":
        return customerService
            .findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
      case "COURIER":
        return courierService
            .findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Courier not found"));
      default:
        throw new UsernameNotFoundException("Invalid role");
    }
  }
}











