package com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.NotificationGenerationStrategy;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NotificationGenerationStrategyFactory {
  private final Map<ChannelType, NotificationGenerationStrategy> generationStrategies;

  public NotificationGenerationStrategyFactory(
      Map<String, NotificationGenerationStrategy> injectedStrategies) {
    this.generationStrategies = new EnumMap<>(ChannelType.class);
    injectedStrategies.values().forEach(strategy -> generationStrategies.put(strategy.channelType(), strategy));
  }

  public NotificationGenerationStrategy getStrategy(ChannelType channelType) {
    NotificationGenerationStrategy strategy = generationStrategies.get(channelType);
    if (strategy != null) {
      return strategy;
    }
    throw new RecordNotFoundException(
        String.format("No notification generation strategy found for channel %s", channelType));
  }
}
