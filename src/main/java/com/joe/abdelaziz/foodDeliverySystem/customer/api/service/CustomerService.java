package com.joe.abdelaziz.foodDeliverySystem.customer.api.service;

import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
  CustomerDTO insertCustomer(CustomerDTO customerDTO);

  CustomerDTO findDtoById(Long id);

  List<CustomerDTO> findAll();

  void updateActiveAddress(Long activeAddressId, Long customerId);

  void clearActiveAddress(Long customerId);

  void removeOwnAddress(Long customerId, Long addressId);

  Optional<Customer> findByPhoneNumber(String phoneNumber);
}
