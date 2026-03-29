package com.joe.abdelaziz.foodDeliverySystem.promotions.internal.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.catalog.api.service.RestaurantService;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderRestaurantDTO;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.dto.PromotionDTO;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.enums.DiscountType;
import com.joe.abdelaziz.foodDeliverySystem.promotions.api.service.PromotionService;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.entity.Promotion;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.promotion.PromotionMapper;
import com.joe.abdelaziz.foodDeliverySystem.promotions.internal.repository.PromotionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

  private final PromotionRepository promotionRepository;
  private final PromotionMapper promotionMapper;
  private final RestaurantService restaurantService;

  public PromotionDTO insertPromotion(PromotionDTO promotionDTO) {

    // A check is made to make sure that a single promo code cannot exist in two
    // records and be active at the same time. So if a promo code is not found, this
    // means an insertion can be made.
    try {
      Promotion existingPromotion = getByCode(promotionDTO.getCode());
      if (existingPromotion.isActive()) {
        throw new BusinessLogicException(
            String.format(
                "You can't have two promo code %s instances active at the same time",
                promotionDTO.getCode()));
      }
    } catch (Exception e) {
    }

    if (promotionDTO.getStartDate().isAfter(promotionDTO.getEndDate())) {
      throw new BusinessLogicException("Start date of a promotion cannot be after end date");
    }

    // If the start date is not yet due, this means the promotion is not active yet.
    if (promotionDTO.getStartDate().isAfter(LocalDateTime.now())) {
      promotionDTO.setActive(false);
    }

    Promotion promotion = promotionMapper.toPromotion(promotionDTO);

    Promotion savedPromotion = promotionRepository.save(promotion);
    return promotionMapper.toPromotionDto(savedPromotion);
  }

  public OrderDTO applyPromotionCode(String promotionCode, OrderDTO order) {
    PromotionDTO promotion = promotionMapper.toPromotionDto(getByCode(promotionCode));
    validatePromotion(promotion);
    order.setPromotionId(promotion.getId());
    order.setPromotionCode(promotion.getCode());
    // If restaurant is not null and has an id, this means that the promotion is
    // associated with that restaurant.
    if (promotion.getRestaurantId() != null) {
      Optional<OrderRestaurantDTO> opFoundOrderRestaurant = order.getRestaurants().stream()
          .filter(
              orderRestaurant -> {
                return promotion.getRestaurantId().equals(orderRestaurant.getRestaurantId());
              })
          .findFirst();
      // Note that the discount may be for the restaurant's delivery fees, percentage
      // on restaurant's items, or fixed discount on all items.
      if (opFoundOrderRestaurant.isPresent()) {
        var foundOrderRestaurant = opFoundOrderRestaurant.get();
        DiscountType discountType = promotion.getDiscountType();
        if (discountType == DiscountType.DELIVERY) {
          BigDecimal discountedDeliveryFee = foundOrderRestaurant
              .getDeliveryFeeCurrentPrice()
              .subtract(promotion.getDiscountValue());
          foundOrderRestaurant.setDeliveryFeeDiscountedPrice(
              discountedDeliveryFee.intValue() < 0 ? BigDecimal.ZERO : discountedDeliveryFee);
        } else if (discountType == DiscountType.FIXED) {
          BigDecimal discountedReceipt = foundOrderRestaurant
              .getTotalRestaurantReceipt()
              .subtract(promotion.getDiscountValue());
          foundOrderRestaurant.setDiscountedTotalRestaurantReceipt(
              discountedReceipt.intValue() < 0 ? BigDecimal.ZERO : discountedReceipt);

        } else if (discountType == DiscountType.PERCENTAGE) {
          BigDecimal discountValue = foundOrderRestaurant
              .getTotalRestaurantReceipt()
              .multiply(promotion.getDiscountValue().divide(new BigDecimal(100)));
          foundOrderRestaurant.setDiscountedTotalRestaurantReceipt(
              foundOrderRestaurant.getTotalRestaurantReceipt().subtract(discountValue));
        }
        calculateDiscountedReceipt(order);
        return order;
        // A customer may not include a promotion's associated restaurant into his
        // order. This means that the promotion code applies to nothing.
      } else {
        throw new BusinessLogicException(
            "The restaurant associated with the promotion does not exist in the list of"
                + " restaurants.");
      }
    }

    if (promotion.getDiscountType() == DiscountType.DELIVERY) {
      order.setDiscountedTotalDeliveryFees(BigDecimal.ZERO);
    } else if (promotion.getDiscountType() == DiscountType.FIXED) {
      order.setDiscountedOrderTotal(order.getOrderTotal().subtract(promotion.getDiscountValue()));
    } else if (promotion.getDiscountType() == DiscountType.PERCENTAGE) {
      order.setDiscountedOrderTotal(
          order.getOrderTotal().multiply(promotion.getDiscountValue().divide(new BigDecimal(100))));
    }

    return order;
  }

  /*
   * Recalculates the receipt of the order after applying a promotion code
   *
   * Note that you first have to check if you have a discount whether on a
   * delivery fee or a restaurant's receipt. The main concern here is to calculate
   * the restaurant that got a discount with restaurants that didn't.
   *
   * @param dto
   */
  private void calculateDiscountedReceipt(OrderDTO dto) {
    BigDecimal discountedTotalDeliveryFees = BigDecimal.ZERO;
    BigDecimal discountedOrderTotal = BigDecimal.ZERO;

    boolean hasReceiptDiscount = dto.getRestaurants().stream()
        .anyMatch(restaurant -> restaurant.getDiscountedTotalRestaurantReceipt() != null);

    if (hasReceiptDiscount) {
      for (OrderRestaurantDTO restaurant : dto.getRestaurants()) {
        if (restaurant.getDiscountedTotalRestaurantReceipt() != null) {
          discountedOrderTotal = discountedOrderTotal.add(restaurant.getDiscountedTotalRestaurantReceipt());
        } else {
          discountedOrderTotal = discountedOrderTotal.add(restaurant.getTotalRestaurantReceipt());
        }
      }
      dto.setDiscountedOrderTotal(discountedOrderTotal);
    }

    boolean hasOrderDeliveryFeeDiscount = dto.getRestaurants().stream()
        .anyMatch(restaurant -> restaurant.getDeliveryFeeDiscountedPrice() != null);

    if (hasOrderDeliveryFeeDiscount) {
      for (OrderRestaurantDTO restaurant : dto.getRestaurants()) {
        if (restaurant.getDeliveryFeeDiscountedPrice() != null) {
          discountedTotalDeliveryFees = discountedTotalDeliveryFees.add(restaurant.getDeliveryFeeDiscountedPrice());
        } else {
          discountedTotalDeliveryFees = discountedTotalDeliveryFees.add(restaurant.getDeliveryFeeCurrentPrice());
        }
      }
      dto.setDiscountedTotalDeliveryFees(discountedTotalDeliveryFees);
    }
  }

  @Transactional
  public void incrementUsageCount(Long promotionId) {
    Promotion promotion = getById(promotionId);
    int usedCount = promotion.getUsedCount();

    if (usedCount >= promotion.getMaxUsers()) {
      throw new BusinessLogicException("Promotion code has exceeded the maximum number of usages");
    }

    promotion.setUsedCount(usedCount + 1);
    promotionRepository.save(promotion);
  }

  public void validatePromotion(PromotionDTO promotion) {
    if (!promotion.isActive()) {
      throw new BusinessLogicException("Promotion code is inactive");
    }

    if (promotion.getStartDate().isAfter(LocalDateTime.now())) {
      throw new BusinessLogicException("Promotion code is not active yet");
    }

    if (promotion.getEndDate().isBefore(LocalDateTime.now())) {
      Promotion existingPromotion = getById(promotion.getId());
      existingPromotion.setActive(false);
      promotionRepository.save(existingPromotion);
      throw new BusinessLogicException("Promotion code is expired");
    }

    if (promotion.getUsedCount() >= promotion.getMaxUsers()) {
      Promotion existingPromotion = getById(promotion.getId());
      existingPromotion.setActive(false);
      promotionRepository.save(existingPromotion);
      throw new BusinessLogicException("Promotion code has exceeded the maximum number of usages");
    }
  }

  public PromotionDTO getDtoById(long id) {
    return promotionMapper.toPromotionDto(getById(id));
  }

  public List<PromotionDTO> getAll() {
    return promotionRepository.findAll().stream()
        .map(promotionMapper::toPromotionDto)
        .toList();
  }

  private Promotion getByCode(String promotionCode) {
    return promotionRepository
        .findByCodeAndActiveTrue(promotionCode)
        .orElseThrow(
            () -> new RecordNotFoundException(
                String.format("Promotion code %s is not found", promotionCode)));
  }

  public PromotionDTO deactivatePromotion(Long id) {
    Promotion promo = getById(id);
    promo.setActive(false);
    return promotionMapper.toPromotionDto(promotionRepository.save(promo));
  }

  public PromotionDTO updatePromotion(PromotionDTO dto) {
    Promotion updatedPromotion = promotionMapper.toUpdatePromotion(dto, getById(dto.getId()));
    return promotionMapper.toPromotionDto(promotionRepository.save(updatedPromotion));
  }

  private Promotion getById(long id) {
    return promotionRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Promotion id %d is not found", id)));
  }

  public void deleteById(Long id) {
    promotionRepository.deleteById(id);
  }
}
