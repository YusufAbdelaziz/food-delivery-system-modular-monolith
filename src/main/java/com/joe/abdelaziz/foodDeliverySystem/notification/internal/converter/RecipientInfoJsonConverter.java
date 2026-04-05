package com.joe.abdelaziz.foodDeliverySystem.notification.internal.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.abdelaziz.foodDeliverySystem.notification.internal.model.RecipientInfo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;

@Converter
public class RecipientInfoJsonConverter implements AttributeConverter<RecipientInfo, String> {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(RecipientInfo recipientInfo) {
    if (recipientInfo == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.writeValueAsString(recipientInfo);
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException("Could not serialize RecipientInfo", exception);
    }
  }

  @Override
  public RecipientInfo convertToEntityAttribute(String json) {
    if (json == null || json.isBlank()) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(json, RecipientInfo.class);
    } catch (IOException exception) {
      throw new IllegalArgumentException("Could not deserialize RecipientInfo", exception);
    }
  }
}
