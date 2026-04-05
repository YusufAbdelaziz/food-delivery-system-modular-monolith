package com.joe.abdelaziz.foodDeliverySystem.orders.api.service;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.command.OrderRatingSubmission;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderPlacementDto;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.enums.OrderStatus;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.view.OrderRatingInfo;
import jakarta.persistence.Tuple;
import java.util.List;

public interface OrderService {
  OrderDTO placeOrder(OrderPlacementDto dto);

  OrderDTO calculateReceipt(OrderDTO order);

  OrderDTO applyPromotionCode(OrderDTO order);

  OrderDTO findDtoById(Long id);

  List<OrderDTO> findAll();

  OrderDTO changeOrderCourier(Long courierId, Long orderId);

  OrderDTO changeOrderStatus(Long orderId, OrderStatus status);

  List<OrderRatingInfo> findOrdersToRate(Long customerId);

  void submitRatings(List<OrderRatingSubmission> submissions);

  List<OrderDTO> findOrdersByCourierId(Long courierId);

  List<Tuple> findCourierStats();

  List<Tuple> findCouriersNumOfSuccessfulOrders();
}
