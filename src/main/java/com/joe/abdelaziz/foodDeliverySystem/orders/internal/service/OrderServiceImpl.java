package com.joe.abdelaziz.foodDeliverySystem.orders.internal.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.service.CustomerService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.command.OrderRatingSubmission;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.command.OrderRestaurantRatingSubmission;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderItemDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderOptionDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderSpecDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.enums.OrderStatus;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderDeliveryFeeService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderPromotionService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.service.OrderService;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.view.OrderRatingInfo;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.Order;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.OrderRestaurant;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.mapper.OrderMapper;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderDeliveryFeeService orderDeliveryFeeService;
  private final CustomerService customerService;
  private final RestaurantService restaurantService;
  private final OrderPromotionService orderPromotionService;
  private final OrderMapper orderMapper;

  public OrderDTO insertOrder(OrderDTO dto) {
    Order order = orderMapper.toOrder(dto);
    if (dto.getCustomerId() == null) {
      throw new BusinessLogicException("A customer should be provided when placing an order");
    }

    if (dto.getRestaurants().isEmpty()) {
      throw new BusinessLogicException("An order must contain at least 1 restaurant");
    }
    CustomerDTO customer = customerService.findDtoById(dto.getCustomerId());
    order.setCustomerId(customer.getId());
    if (dto.getPromotionId() != null) {
      orderPromotionService.validatePromotionForOrder(dto.getPromotionId());
      order.setPromotionId(dto.getPromotionId());
    }

    // Estimated delivery date should be recalculated because the user may
    // add new address, review the order, etc so that would consume a couple of
    // minutes
    order.setEstimatedDeliveryDate(calculateOverallEstimatedDeliveryTime(order.getRestaurants()));
    order.setStatus(OrderStatus.PENDING);

    for (OrderRestaurant orderRestaurant : order.getRestaurants()) {
      orderRestaurant.setOrder(order);
    }

    Order newOrder = orderRepository.save(order);

    if (order.getPromotionId() != null) {
      orderPromotionService.registerPromotionUsage(order.getPromotionId(), customer.getId());
    }

    return orderMapper.toOrderDTO(newOrder);
  }

  public OrderDTO calculateReceipt(OrderDTO order) {
    CustomerDTO customer = customerService.findDtoById(order.getCustomerId());
    for (OrderRestaurantDTO orderRestaurant : order.getRestaurants()) {
      BigDecimal restaurantOrderTotal = calculateRestaurantReceiptWithoutDeliveryFee(orderRestaurant);
      order.setOrderTotal(order.getOrderTotal().add(restaurantOrderTotal));
      OrderRestaurantDeliveryFeeDTO restaurantDeliveryFee = orderDeliveryFeeService
          .calculateDeliveryFeeForRestaurant(orderRestaurant, customer);
      orderRestaurant.setDeliveryFeeId(restaurantDeliveryFee.getId());
      orderRestaurant.setDeliveryFeeCurrentPrice(restaurantDeliveryFee.getCurrentPrice());
      orderRestaurant.setDeliveryFeeDiscountedPrice(restaurantDeliveryFee.getDiscountedPrice());
      orderRestaurant.setEstimatedDeliveryDuration(restaurantDeliveryFee.getEstimatedDuration());
      BigDecimal deliveryFeePrice = orderRestaurant.getDeliveryFeeCurrentPrice();
      order.setTotalDeliveryFees(order.getTotalDeliveryFees().add(deliveryFeePrice));
    }

    order.setEstimatedDeliveryDate(calculateOverallEstimatedDeliveryTime(order.getRestaurants()));
    return order;
  }

  private <T> LocalDateTime calculateOverallEstimatedDeliveryTime(Set<T> restaurants) {
    // Adds 15 minutes for each additional restaurant.
    int additionalBufferMinutes = 15 * (restaurants.size() - 1);

    Duration maxDuration = restaurants.stream()
        .map(restaurant -> getEstimatedDuration(restaurant))
        .max(Duration::compareTo)
        .orElseThrow(() -> new IllegalArgumentException("No restaurants in the order"));

    return LocalDateTime.now()
        .plusMinutes(additionalBufferMinutes)
        .plusSeconds(maxDuration.getSeconds());
  }

  private Duration getEstimatedDuration(Object restaurant) {
    if (restaurant instanceof OrderRestaurantDTO) {
      return ((OrderRestaurantDTO) restaurant).getEstimatedDeliveryDuration();
    } else if (restaurant instanceof OrderRestaurant) {
      return ((OrderRestaurant) restaurant).getEstimatedDeliveryDuration();
    } else {
      throw new IllegalArgumentException("Unsupported restaurant type");
    }
  }

  private BigDecimal calculateRestaurantReceiptWithoutDeliveryFee(
      OrderRestaurantDTO orderRestaurant) {
    BigDecimal totalRestaurantReceipt = BigDecimal.ZERO;
    for (OrderItemDTO orderItem : orderRestaurant.getItems()) {
      BigDecimal itemPrice = orderItem.getPrice();
      for (OrderSpecDTO spec : orderItem.getSpecs()) {
        for (OrderOptionDTO option : spec.getOptions()) {
          itemPrice = itemPrice.add(option.getPrice());
        }
      }
      itemPrice = itemPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
      totalRestaurantReceipt = totalRestaurantReceipt.add(itemPrice);
    }

    orderRestaurant.setTotalRestaurantReceipt(totalRestaurantReceipt);
    return orderRestaurant.getTotalRestaurantReceipt();
  }

  public OrderDTO applyPromotionCode(OrderDTO order) {
    if (order.getPromotionCode() == null || order.getPromotionCode().isEmpty()) {
      throw new BusinessLogicException("You should provide a valid promotion code");
    }
    return orderPromotionService.applyPromotionCode(order.getPromotionCode(), order);
  }

  public OrderDTO findDtoById(Long id) {
    Order order = orderRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Order id %d is not found", id)));
    return orderMapper.toOrderDTO(order);
  }

  public Order findById(Long id) {
    Order order = orderRepository
        .findById(id)
        .orElseThrow(
            () -> new RecordNotFoundException(String.format("Order id %d is not found", id)));
    return order;
  }

  public List<OrderDTO> findAll() {
    return orderRepository.findAll().stream()
        .map(courier -> orderMapper.toOrderDTO(courier))
        .toList();
  }

  public OrderDTO changeOrderCourier(Long courierId, Long orderId) {
    Order order = findById(orderId);
    order.setCourierId(courierId);
    return orderMapper.toOrderDTO(orderRepository.save(order));
  }

  @Transactional
  public OrderDTO changeOrderStatus(Long orderId, OrderStatus status) {
    Order existingOrder = findById(orderId);
    existingOrder.setStatus(status);
    OrderDTO updatedOrderDTO = orderMapper.toOrderDTO(orderRepository.save(existingOrder));
    if (status == OrderStatus.SUCCESSFUL) {
      restaurantService.incrementSuccessfulOrdersCount(
          existingOrder.getRestaurants().stream().map(OrderRestaurant::getRestaurantId).toList());
    }
    return updatedOrderDTO;
  }

  public List<OrderRatingInfo> findOrdersToRate(Long customerId) {
    List<OrderRatingInfo> ordersToRate = orderRepository.findUnratedOrdersByCustomerId(customerId);
    return ordersToRate;
  }

  @Transactional
  public void submitRatings(List<OrderRatingSubmission> submissions) {
    for (OrderRatingSubmission submission : submissions) {
      Order order = findById(submission.id());

      order.setCourierRating(submission.courierRating());
      order.setCourierFeedback(submission.courierFeedback());

      for (OrderRestaurantRatingSubmission restaurantRating : submission.restaurants()) {
        OrderRestaurant orderRestaurant = order.getRestaurants().stream()
            .filter(r -> r.getId().equals(restaurantRating.id()))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("OrderRestaurant not found"));

        orderRestaurant.setRestaurantRating(restaurantRating.restaurantRating());
        orderRestaurant.setRestaurantFeedback(restaurantRating.restaurantFeedback());
      }

      orderRepository.save(order);
    }
  }

  public List<OrderDTO> findOrdersByCourierId(Long courierId) {
    return orderRepository.findNotFinishedOrdersByCourierId(courierId).stream()
        .map(order -> orderMapper.toOrderDTO(order))
        .toList();
  }

  public List<Tuple> findCourierStats() {
    return orderRepository.findCouriersStats();
  }

  public List<Tuple> findCouriersNumOfSuccessfulOrders() {
    return orderRepository.findCouriersNumOfSuccessfulOrders();
  }
}
