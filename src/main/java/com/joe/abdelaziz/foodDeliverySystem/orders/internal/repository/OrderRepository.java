package com.joe.abdelaziz.foodDeliverySystem.orders.internal.repository;
import com.joe.abdelaziz.foodDeliverySystem.orders.internal.entity.*;

import com.joe.abdelaziz.foodDeliverySystem.orders.api.view.OrderRatingInfo;
import jakarta.persistence.Tuple;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(
      value =
          "SELECT o FROM Order o WHERE o.customerId = :customerId AND o.courierRating IS NULL AND"
              + " o.status = 'SUCCESSFUL'")
  List<OrderRatingInfo> findUnratedOrdersByCustomerId(Long customerId);

  @Query(
      value = "SELECT o FROM Order o WHERE o.courierId = :courierId AND o.status != 'SUCCESSFUL'")
  List<Order> findNotFinishedOrdersByCourierId(Long courierId);

  @Query(
      "SELECT o.courierId AS courierId, o.discountedTotalDeliveryFees AS"
          + " discountedTotalDeliveryFees, o.totalDeliveryFees AS totalDeliveryFees,"
          + " o.courierRating AS courierRating FROM Order o WHERE o.status = 'SUCCESSFUL' AND"
          + " o.courierRating IS NOT NULL AND o.courierId IS NOT NULL")
  List<Tuple> findCouriersStats();

  @Query(
      value =
          "SELECT o.courierId AS courierId, COUNT(o.id) AS totalSuccessfulOrders "
              + "FROM Order o "
              + "WHERE o.status = 'SUCCESSFUL' AND o.courierId IS NOT NULL "
              + "GROUP BY o.courierId "
              + "HAVING COUNT(o.id) > 0",
      nativeQuery = false)
  List<Tuple> findCouriersNumOfSuccessfulOrders();
}








