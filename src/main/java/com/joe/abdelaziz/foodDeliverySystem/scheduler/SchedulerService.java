package com.joe.abdelaziz.foodDeliverySystem.scheduler;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.service.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
  private final CourierService courierService;
  private final RestaurantService restaurantService;

  @Scheduled(fixedRateString = "${scheduler.update-restaurant-ratings.rate}")
  void updateRestaurantsAvgRating() {
    restaurantService.updateAvgRatingsForAllRestaurants();
  }

  @Scheduled(fixedRateString = "${scheduler.update-courier-ratings.rate}")
  void updateCouriersStats() {
    courierService.updateCouriersStats();
  }

  @Scheduled(fixedRateString = "${scheduler.update-courier-ratings.rate}")
  void updateCourierTotalOrders() {
    courierService.updateCouriersTotalOrders();
  }
}







