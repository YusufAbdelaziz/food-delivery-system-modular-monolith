package com.joe.abdelaziz.foodDeliverySystem.notification.internal.strategy;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.notification.api.enums.ChannelType;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.entity.Notification;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import com.joe.abdelaziz.foodDeliverySystem.orders.api.dto.OrderDTO;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailNotificationStrategy extends BaseNotificationGenerationStrategy {
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final BuildEmailNotificationContextUseCase buildEmailNotificationContextUseCase;

  @Value("${spring.mail.username:}")
  private String fromAddress;

  public EmailNotificationStrategy(
      JavaMailSender mailSender,
      SpringTemplateEngine templateEngine,
      BuildEmailNotificationContextUseCase buildEmailNotificationContextUseCase) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
    this.buildEmailNotificationContextUseCase = buildEmailNotificationContextUseCase;
  }

  @Override
  public ChannelType channelType() {
    return ChannelType.EMAIL;
  }

  @Override
  public RecipientInfo generate(RecipientInfo recipientInfo) {
    return requireEmail(recipientInfo, ChannelType.EMAIL);
  }

  @Override
  public void execute(Notification notification, OrderDTO order) {
    Objects.requireNonNull(notification, "notification must not be null");

    RecipientInfo recipientInfo = requireEmail(notification.getRecipientInfo(), ChannelType.EMAIL);

    try {
      var context = buildEmailNotificationContextUseCase.build(notification, order);
      String htmlBody = templateEngine.process("notification/order-receipt-email", context);

      var mimeMessage = mailSender.createMimeMessage();
      var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      if (fromAddress != null && !fromAddress.isBlank()) {
        String safeFromAddress = asNonNull(fromAddress, "mail from address");
        helper.setFrom(safeFromAddress);
      }
      String recipientEmail = asNonNull(recipientInfo.getEmail(), "recipient email");
      String safeSubject = asNonNull(resolveSubject(order), "email subject");
      helper.setTo(recipientEmail);
      helper.setSubject(safeSubject);
      helper.setText(htmlBody != null ? htmlBody : "", true);

      mailSender.send(mimeMessage);
      log.info("Email notification id={} sent to={}", notification.getId(), recipientInfo.getEmail());
    } catch (MessagingException | RuntimeException exception) {
      throw new BusinessLogicException("Failed to send email notification", exception);
    }
  }

  private @NonNull String resolveSubject(OrderDTO order) {
    if (order != null && order.getId() != null) {
      return String.format("Order Receipt #%d", order.getId());
    }
    return "Order Receipt";
  }

  private @NonNull String asNonNull(String value, String fieldName) {
    if (value == null) {
      throw new BusinessLogicException(String.format("%s must not be null", fieldName));
    }
    return value;
  }
}
