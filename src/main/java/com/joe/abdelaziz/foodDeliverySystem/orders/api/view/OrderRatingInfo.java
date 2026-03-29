package com.joe.abdelaziz.foodDeliverySystem.orders.api.view;

import java.util.List;

public interface OrderRatingInfo {
  Long getId();

  Byte getCourierRating();

  String getCourierFeedback();

  List<OrderRestaurantRatingInfo> getRestaurants();
}








