package com.joe.abdelaziz.foodDeliverySystem.customer.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.OrderCustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.customer.internal.entity.Customer;

@Mapper
public abstract class CustomerMapper {
  @Mapping(target = "activeAddressId", source = "activeAddressId")
  public abstract CustomerDTO toCustomerDTO(Customer customer);

  @Mapping(target = "activeAddressId", source = "activeAddressId")
  public abstract OrderCustomerDTO toOrderCustomerDTO(Customer customer);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "role", ignore = true),
      @Mapping(target = "authorities", ignore = true),
      @Mapping(target = "activeAddressId", source = "activeAddressId")
  })
  public abstract Customer toCustomer(CustomerDTO customerDTO);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "role", ignore = true),
      @Mapping(target = "password", ignore = true),
      @Mapping(target = "activeAddressId", source = "activeAddressId"),
      @Mapping(target = "authorities", ignore = true)
  })
  public abstract Customer toCustomer(OrderCustomerDTO orderCustomerDTO);
}
