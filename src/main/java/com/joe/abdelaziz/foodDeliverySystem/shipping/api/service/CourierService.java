package com.joe.abdelaziz.foodDeliverySystem.shipping.api.service;

import java.util.List;
import java.util.Optional;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.CourierDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.internal.entity.Courier;
import org.springframework.security.core.userdetails.UserDetails;

public interface CourierService {
  CourierDTO insertCourier(CourierDTO dto);

  Courier findById(Long id);

  CourierDTO findDtoById(Long id);

  List<CourierDTO> findAll();

  OrderDTO assignOrderToCourier(Long courierId, Long orderId);

  CourierDTO updateCourier(CourierDTO dto);

  Optional<UserDetails> findByPhoneNumber(String phoneNumber);

  Optional<Long> findIdByPhoneNumber(String phoneNumber);

  List<OrderDTO> findOrdersByCourierId(Long courierId);

  void updateCouriersStats();

  void updateCouriersTotalOrders();
}
