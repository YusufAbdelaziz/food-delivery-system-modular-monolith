package com.joe.abdelaziz.foodDeliverySystem.notification.internal.factory;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.RecordNotFoundException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.BuildEmailNotificationContextUseCase;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.EmailNotificationStrategy;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.NotificationGenerationStrategy;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy.SmsNotificationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

class NotificationGenerationStrategyFactoryTest {

  @Test
  void shouldReturnMatchedStrategy() {
    NotificationGenerationStrategy emailStrategy =
        new EmailNotificationStrategy(
            mock(JavaMailSender.class),
            mock(SpringTemplateEngine.class),
            mock(BuildEmailNotificationContextUseCase.class));
    NotificationGenerationStrategy smsStrategy = new SmsNotificationStrategy();
    NotificationGenerationStrategyFactory factory =
        new NotificationGenerationStrategyFactory(
            Map.of("emailNotificationStrategy", emailStrategy, "smsNotificationStrategy", smsStrategy));

    NotificationGenerationStrategy strategy = factory.getStrategy(ChannelType.SMS);

    assertSame(smsStrategy, strategy);
  }

  @Test
  void shouldFailWhenNoStrategyExistsForType() {
    NotificationGenerationStrategy emailStrategy =
        new EmailNotificationStrategy(
            mock(JavaMailSender.class),
            mock(SpringTemplateEngine.class),
            mock(BuildEmailNotificationContextUseCase.class));
    NotificationGenerationStrategyFactory factory =
        new NotificationGenerationStrategyFactory(Map.of("emailNotificationStrategy", emailStrategy));

    assertThrows(RecordNotFoundException.class, () -> factory.getStrategy(ChannelType.WHATSAPP));
  }
}
