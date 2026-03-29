package com.joe.abdelaziz.foodDeliverySystem.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {
  private Boolean success;
  private String message;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
  private LocalDateTime dateTime;
  private List<String> details;

  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
    this.success = Boolean.FALSE;
    this.dateTime = LocalDateTime.now();
  }

}









