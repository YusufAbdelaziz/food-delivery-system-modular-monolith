package com.joe.abdelaziz.foodDeliverySystem.location.internal.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.AddressDTO;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.AddressService;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.entity.Address;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.mapper.AddressMapper;
import com.joe.abdelaziz.foodDeliverySystem.location.internal.repository.AddressRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final AddressMapper addressMapper;

  @Override
  public AddressDTO findDTOById(@NonNull Long id) {
    return addressMapper.toAddressDto(findById(id));
  }

  private Address findById(@NonNull Long id) {
    return addressRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Address id %d is not found", id)));
  }

  @Override
  public List<AddressDTO> findAddressesByCustomerId(@NonNull Long id) {

    return addressRepository.findByCustomerId(id).stream()
        .map(address -> addressMapper.toAddressDto(address))
        .toList();
  }

  @Override
  public void setAllAddressesActivityToFalse(@NonNull Long activeAddressId, @NonNull Long userId) {
    addressRepository.updateAddressActivityByUserId(activeAddressId, userId);
  }

  @Override
  public AddressDTO insertAddress(@NonNull AddressDTO dto, @NonNull Long userId) {

    Address address = addressMapper.toAddress(dto);
    address.setCustomerId(userId);
    Address insertedAddress = addressRepository.save(address);

    if (Boolean.TRUE.equals(insertedAddress.getActive())) {
      setAllAddressesActivityToFalse(insertedAddress.getId(), userId);
    }
    return addressMapper.toAddressDto(insertedAddress);
  }

  @Override
  public AddressDTO setAddressToActive(@NonNull Long addressId) {
    Address address = findById(addressId);
    Long customerId = address.getCustomerId();
    if (customerId == null) {
      throw new BusinessLogicException("Only customer addresses can be set as active");
    }
    address.setActive(true);
    setAllAddressesActivityToFalse(addressId, customerId);
    return addressMapper.toAddressDto(addressRepository.save(address));
  }

  @Transactional
  @Override
  public void deleteCustomerAddress(@NonNull Long customerId, @NonNull Long addressId) {
    Address address = findById(addressId);
    if (!customerId.equals(address.getCustomerId())) {
      throw new BusinessLogicException("Customer cannot delete an address he does not own");
    }
    addressRepository.delete(address);
  }

  @Transactional
  @Override
  public void deleteRestaurantAddress(@NonNull Long restaurantId, @NonNull Long addressId) {
    Address address = findById(addressId);
    long matchingRestaurants =
        addressRepository.countRestaurantsByRestaurantIdAndAddressId(restaurantId, addressId);
    if (matchingRestaurants == 0) {
      throw new BusinessLogicException("Restaurant address does not belong to the provided restaurant");
    }
    addressRepository.clearRestaurantAddress(restaurantId, addressId);
    addressRepository.delete(address);
  }

}
