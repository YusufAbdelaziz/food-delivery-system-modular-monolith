package com.joe.abdelaziz.foodDeliverySystem.location.api.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.AddressDTO;

public interface AddressService {
  AddressDTO findDTOById(@NonNull Long id);

  List<AddressDTO> findAddressesByCustomerId(@NonNull Long id);

  void setAllAddressesActivityToFalse(@NonNull Long activeAddressId, @NonNull Long userId);

  AddressDTO insertAddress(@NonNull AddressDTO dto, @NonNull Long userId);

  AddressDTO setAddressToActive(@NonNull Long addressId);

  void deleteCustomerAddress(@NonNull Long customerId, @NonNull Long addressId);

  void deleteRestaurantAddress(@NonNull Long restaurantId, @NonNull Long addressId);

}
