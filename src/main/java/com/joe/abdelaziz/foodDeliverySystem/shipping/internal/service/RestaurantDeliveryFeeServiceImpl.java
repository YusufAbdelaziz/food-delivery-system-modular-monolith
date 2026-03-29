package com.joe.abdelaziz.foodDeliverySystem.shipping.internal.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.DeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.dto.RestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.DeliveryFeeService;
import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.location.api.dto.AddressDTO;
import com.joe.abdelaziz.foodDeliverySystem.location.api.service.AddressService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderDeliveryFeeService;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.RestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.service.RestaurantDeliveryFeeService;
import com.joe.abdelaziz.foodDeliverySystem.shipping.internal.mapper.RestaurantDeliveryFeeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantDeliveryFeeServiceImpl
    implements RestaurantDeliveryFeeService, OrderDeliveryFeeService {

  private final RestaurantDeliveryFeeMapper orderDeliveryFeeMapper;
  private final DeliveryFeeService deliveryFeeService;
  private final RestaurantService restaurantService;
  private final AddressService addressService;

  public RestaurantDeliveryFeeDTO calculateRestaurantDeliveryFee(
      @NonNull OrderRestaurantDTO orderRestaurant, @NonNull CustomerDTO customer) {

    AddressDTO customerAddress = addressService.findDTOById(customer.getActiveAddressId());
    Long toRegionId = customerAddress.getRegionId();
    RestaurantDTO restaurant = restaurantService.findDtoById(orderRestaurant.getRestaurantId());
    AddressDTO restaurantAddress = addressService.findDTOById(restaurant.getAddressId());
    Long fromRegionId = restaurantAddress.getRegionId();
    DeliveryFeeDTO deliveryFee = deliveryFeeService.findDeliveryFeeBetweenTwoRegions(toRegionId,
        fromRegionId);
    return orderDeliveryFeeMapper.toRestaurantDeliveryFeeDTO(
        orderDeliveryFeeMapper.toRestaurantDeliveryFee(deliveryFee));
  }

  @Override
  public OrderRestaurantDeliveryFeeDTO calculateDeliveryFeeForRestaurant(
      @NonNull OrderRestaurantDTO orderRestaurant, @NonNull CustomerDTO customer) {
    RestaurantDeliveryFeeDTO deliveryFee = calculateRestaurantDeliveryFee(orderRestaurant, customer);
    OrderRestaurantDeliveryFeeDTO result = new OrderRestaurantDeliveryFeeDTO();
    result.setId(deliveryFee.getId());
    result.setCurrentPrice(deliveryFee.getCurrentPrice());
    result.setDiscountedPrice(deliveryFee.getDiscountedPrice());
    result.setEstimatedDuration(deliveryFee.getEstimatedDuration());
    return result;
  }
}
