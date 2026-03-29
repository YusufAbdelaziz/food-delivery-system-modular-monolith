package com.joe.abdelaziz.foodDeliverySystem.orders.api.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderRatingSubmission(
    @NotNull Long id,
    @NotNull @Min(1) @Max(5) Byte courierRating,
    @Size(max = 2000) String courierFeedback,
    List<OrderRestaurantRatingSubmission> restaurants) {}








