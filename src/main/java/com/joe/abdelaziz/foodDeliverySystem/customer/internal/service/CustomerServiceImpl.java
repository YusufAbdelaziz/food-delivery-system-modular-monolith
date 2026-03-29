package com.joe.abdelaziz.foodDeliverySystem.customer.internal.service;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.*;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.service.CustomerService;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.entity.Customer;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.mapper.CustomerMapper;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.repository.CustomerRepository;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.Role;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.service.RoleService;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.AddressService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final RoleService roleService;
  private final @Lazy AddressService addressService;

  @Transactional
  public CustomerDTO insertCustomer(CustomerDTO customerDTO) {
    Customer customer = customerMapper.toCustomer(customerDTO);
    Role role = roleService.findRoleByType(RoleType.USER);
    customer.setRole(role);
    return customerMapper.toCustomerDTO(customerRepository.save(customer));
  }

  public CustomerDTO findDtoById(Long id) {
    Customer user = customerRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", id)));
    return customerMapper.toCustomerDTO(user);
  }

  public List<CustomerDTO> findAll() {
    return customerRepository.findAllByRoleType(RoleType.USER).stream()
        .map(customerMapper::toCustomerDTO)
        .toList();
  }

  public void updateActiveAddress(Long activeAddressId, Long customerId) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", customerId)));
    customer.setActiveAddressId(activeAddressId);
    customerRepository.save(customer);
  }

  public void clearActiveAddress(Long customerId) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", customerId)));
    customer.setActiveAddressId(null);
    customerRepository.save(customer);
  }

  public void removeOwnAddress(Long customerId, Long addressId) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", customerId)));
    if (addressId.equals(customer.getActiveAddressId())) {
      customer.setActiveAddressId(null);
      customerRepository.save(customer);
    }
    addressService.deleteCustomerAddress(customerId, addressId);
  }

  public Optional<Customer> findByPhoneNumber(String phoneNumber) {
    return customerRepository.findByPhoneNumber(phoneNumber);
  }
}









