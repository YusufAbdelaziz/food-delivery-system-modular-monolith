package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

class NotificationGenerationStrategiesTest {

  @Test
  void emailStrategyShouldGenerateRecipientInfo() {
    EmailNotificationStrategy strategy =
        new EmailNotificationStrategy(
            mock(JavaMailSender.class),
            mock(SpringTemplateEngine.class),
            mock(BuildEmailNotificationContextUseCase.class));

    RecipientInfo generated =
        strategy.generate(
            RecipientInfo.builder()
                .email("user@example.com")
                .phone("+20100111222")
                .build());

    assertEquals(RecipientInfo.builder().email("user@example.com").build(), generated);
  }

  @Test
  void emailStrategyShouldFailWhenEmailIsMissing() {
    EmailNotificationStrategy strategy =
        new EmailNotificationStrategy(
            mock(JavaMailSender.class),
            mock(SpringTemplateEngine.class),
            mock(BuildEmailNotificationContextUseCase.class));

    assertThrows(BusinessLogicException.class, () -> strategy.generate(RecipientInfo.builder().build()));
  }

  @Test
  void smsStrategyShouldGenerateRecipientInfo() {
    SmsNotificationStrategy strategy = new SmsNotificationStrategy();

    RecipientInfo generated =
        strategy.generate(
            RecipientInfo.builder()
                .phone("+201001234567")
                .email("user@example.com")
                .build());

    assertEquals(RecipientInfo.builder().phone("+201001234567").build(), generated);
  }

  @Test
  void smsStrategyShouldFailWhenPhoneIsMissing() {
    SmsNotificationStrategy strategy = new SmsNotificationStrategy();

    assertThrows(
        BusinessLogicException.class,
        () -> strategy.generate(RecipientInfo.builder().email("u@example.com").build()));
  }

  @Test
  void whatsAppStrategyShouldGenerateRecipientInfo() {
    WhatsAppNotificationStrategy strategy = new WhatsAppNotificationStrategy();

    RecipientInfo generated = strategy.generate(RecipientInfo.builder().phone("+201001234568").build());

    assertEquals(RecipientInfo.builder().phone("+201001234568").build(), generated);
  }

  @Test
  void whatsAppStrategyShouldFailWhenPhoneIsMissing() {
    WhatsAppNotificationStrategy strategy = new WhatsAppNotificationStrategy();

    assertThrows(
        BusinessLogicException.class,
        () -> strategy.generate(RecipientInfo.builder().email("u@example.com").build()));
  }
}
